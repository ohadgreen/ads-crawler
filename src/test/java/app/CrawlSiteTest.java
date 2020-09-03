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
        String url1 = "https://www.filmmaking.net";
        CrawlSite crawlSite = new CrawlSite(url1);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void readUrlForSellerInfoWrongUrl() throws IOException {
        String falseUrl = "xyz";
        CrawlSite crawlSite = new CrawlSite(falseUrl);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        assertEquals(0, sellerLineSet.size());
    }
}