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
        String sheredais = "https://sharedais.com";
        CrawlSite crawlSite = new CrawlSite(sheredais);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void testUrlProblem() throws IOException {
        String panolian = "http://panolian.com";
        String newCentral = "https://thenewscentral.org";

        CrawlSite crawlSite = new CrawlSite(panolian);
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