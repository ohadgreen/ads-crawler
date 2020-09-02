package app;

import app.crawler.CrawlSite;
import model.AdsSeller;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

public class CrawlSiteTest {

    @Test
    public void readUrlForSellerInfoSimpleTest() throws IOException {
        CrawlSite crawlSite = new CrawlSite();
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo("https://www.filmmaking.net/ads.txt");

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void readUrlForSellerInfoWrongUrl() throws IOException {
        CrawlSite crawlSite = new CrawlSite();
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo("https://xxx/feed/ads.txt/");

        assertEquals(0, sellerLineSet.size());
    }
}