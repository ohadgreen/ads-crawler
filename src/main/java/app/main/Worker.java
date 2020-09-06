package app.main;

import app.crawler.CrawlSite;
import app.update_site_ads.UpdateSiteAds;
import app.update_site_ads.UpdateSiteAdsToMySqlTable;
import model.AdsSeller;
import model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.Set;

public class Worker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Site workerSite;
    private Properties properties;
    private String insertStatement;

    public Worker(Site workerSite, Properties properties, String insertStatement) {
        this.workerSite = workerSite;
        this.properties = properties;
        this.insertStatement = insertStatement;
    }

    @Override
    public void run() {
        CrawlSite crawlSite = new CrawlSite(workerSite.getSiteUrl());
        final Set<AdsSeller> adsSellers = crawlSite.readUrlForSellerInfo();

        UpdateSiteAds updateSiteAds = new UpdateSiteAdsToMySqlTable(workerSite.getSiteId(), adsSellers, insertStatement, properties);
        updateSiteAds.updateAdsList();

    }
}
