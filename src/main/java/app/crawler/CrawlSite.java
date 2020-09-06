package app.crawler;

import model.AdsSeller;

import java.util.Set;

public interface CrawlSite {
    Set<AdsSeller> readUrlForSellerInfo();
}
