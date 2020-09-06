package app.main;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromDb;
import app.load_sites_list.LoadSitesFromFile;
import app.update_sites.UpdateSiteList;
import app.update_sites.UpdateSitesToMySqlTable;
import db.MySqlConnection;
import model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private Properties properties = new Properties();
    private Set<Site> siteSet;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.loadProperties();
        mainApp.loadSites();
        try {
            mainApp.workManager();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() {
        String fileName = "application.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fileName)) {
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadSites() {
        int sitesCount = 0;
        try(Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"))) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(1) from " + properties.getProperty("SITES_TABLE"));
            while (resultSet.next()) {
               sitesCount = resultSet.getInt(1);
            }
            if (sitesCount == 0) {
                // load sites list from file
                LoadSiteList loadSiteListFromFile = new LoadSitesFromFile();
                siteSet = loadSiteListFromFile.getSiteSet(properties);
                // update database sites list from file
                UpdateSiteList updateSiteListInDb = new UpdateSitesToMySqlTable(siteSet, properties);
            }
            else {
                // load sites from database
                LoadSiteList loadSiteListFromDb = new LoadSitesFromDb();
                siteSet = loadSiteListFromDb.getSiteSet(properties);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void workManager() throws InterruptedException {
        Set<Site> siteSetLimited = siteSet.stream().limit(20000).collect(Collectors.toSet());
        String insertStatement = "insert into " + properties.getProperty("SITES_ADS_TABLE")+ " (site_id, exchange_domain, seller_account, payment_type, tag_id)" +
                " values (?, ?, ?, ?, ?)";
        // create ExecutorService to manage threads
        ExecutorService threadExecutor = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("THREADS_NUMBER")));

        Iterator<Site> siteIterator = siteSetLimited.iterator();
        int sitesCount = 0;
        while (siteIterator.hasNext()) {
            Site site = siteIterator.next();
            logger.debug("siteCount: " + sitesCount + " - " + site.getSiteId().toString());
            if (site.getSiteUrl() != null) {
                Worker worker = new Worker(site, properties, insertStatement);
                threadExecutor.execute(worker);
            }
            sitesCount ++;
        }

        threadExecutor.shutdown();
        if (!threadExecutor.awaitTermination(1, TimeUnit.HOURS)) {
            System.err.println("Pool did not terminate yet...");
            threadExecutor.shutdownNow();
        }

    }
}
