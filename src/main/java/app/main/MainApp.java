package app.main;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromDb;
import app.load_sites_list.LoadSitesFromFile;
import model.Site;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainApp {
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
        Set<Site> siteSetLimited = allSites.stream().limit(20).collect(Collectors.toSet());
        String insertStatement = "insert into sites_ads (site_id, exchange_domain, seller_account, payment_type, tag_id)" +
                " values (?, ?, ?, ?, ?)";
        // create ExecutorService to manage threads
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3 );

        Iterator<Site> siteIterator = siteSetLimited.iterator();
        while (siteIterator.hasNext()) {
            Site site = siteIterator.next();
            if (site.getSiteUrl() != null) {
                Worker worker = new Worker(site, properties, insertStatement);
                threadExecutor.execute(worker);
            }
        }

        Thread.sleep(10000);
//        threadExecutor.shutdownNow();
        if (!threadExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.err.println("Pool did not terminate");
            threadExecutor.shutdownNow();
        }

    }
}
