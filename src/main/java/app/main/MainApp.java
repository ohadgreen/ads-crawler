package app.main;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromDb;
import app.load_sites_list.LoadSitesFromFile;
import model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
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

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.loadProperties();
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

    private void workManager() throws InterruptedException {
        LoadSiteList loadSiteList = new LoadSitesFromDb(); // LoadSitesFromFile();
        Set<Site> allSites = loadSiteList.getSiteSet(this.properties);
        Set<Site> siteSetLimited = allSites.stream().limit(100).collect(Collectors.toSet());
        String insertStatement = "insert into sites_ads (site_id, exchange_domain, seller_account, payment_type, tag_id)" +
                " values (?, ?, ?, ?, ?)";
        // create ExecutorService to manage threads
        ExecutorService threadExecutor = Executors.newFixedThreadPool(5 );

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

//        Thread.sleep(30000);
        threadExecutor.shutdown();
        if (!threadExecutor.awaitTermination(1, TimeUnit.HOURS)) {
            System.err.println("Pool did not terminate yet...");
            threadExecutor.shutdownNow();
        }

    }
}
