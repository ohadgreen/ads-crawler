package app.main;


import app.crawler.CrawlSite;
import model.Site;

import java.io.IOException;

public class Worker implements Runnable {

    private Site workerSite;

    public Worker(Site workerSite) {
        this.workerSite = workerSite;
    }

    @Override
    public void run() {
        CrawlSite crawlSite = new CrawlSite(workerSite.getSiteUrl());
        try {
            crawlSite.readUrlForSellerInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
