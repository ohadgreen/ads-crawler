package app.main;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromFile;
import model.Site;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        try {
            mainApp.workManager();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void workManager() throws InterruptedException {
        LoadSiteList loadSiteList = new LoadSitesFromFile();
        Set<Site> allSites = loadSiteList.getSiteSet();
        Set<Site> siteSetLimited = allSites.stream().limit(20).collect(Collectors.toSet());

        // create ExecutorService to manage threads
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3 );

        Iterator<Site> siteIterator = siteSetLimited.iterator();
        while (siteIterator.hasNext()) {
            Site site = siteIterator.next();
            if (site.getSiteUrl() != null) {
                Worker worker = new Worker(site);
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
