package app;

import app.crawler.CrawlSite;
import model.AdsSeller;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

public class CrawlSiteTest {

    @Test
    public void readUrlForSellerInfoSimpleTest() {
        String sheredais = "https://sharedais.com";
        CrawlSite crawlSite = new CrawlSite(sheredais);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void testUrlProblem() {
        String panolian = "http://panolian.com";
        String newCentral = "https://thenewscentral.org";

        CrawlSite crawlSite = new CrawlSite(panolian);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void readUrlForSellerInfoWrongUrl() {
        String falseUrl = "xyz";
        CrawlSite crawlSite = new CrawlSite(falseUrl);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        assertEquals(0, sellerLineSet.size());
    }

    @Test
    public void testLongPaymentType() {
        String smoking = "https://www.smokingmeatforums.com";
//        google.com, pub-4968145218643279, DIRECT  f08c47fec0942fa0
        CrawlSite crawlSite = new CrawlSite(smoking);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        for (AdsSeller adsSeller : sellerLineSet) {
            if (adsSeller.getPaymentsType().length() > 10) {
                System.out.println(adsSeller);
            }
        }
    }
    @Test
    public void testDuplicate() {
        String smoking = "https://www.smokingmeatforums.com";
        CrawlSite crawlSite = new CrawlSite(smoking);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        for (AdsSeller adsSeller : sellerLineSet) {
            if (adsSeller.getTagId().equals("f5ab79cb980f11d1")) {
                System.out.println(adsSeller);
            }
        }
    }

    @Test
    public void testMissingSellerAccunt() {
        String smoking = "https://forum.gsmtutors.com";
//        google.com, pub-4968145218643279, DIRECT  f08c47fec0942fa0
        CrawlSite crawlSite = new CrawlSite(smoking);
        Set<AdsSeller> sellerLineSet = crawlSite.readUrlForSellerInfo();

        for (AdsSeller adsSeller : sellerLineSet) {
            if (adsSeller.getSellerAccountId() == null) {
                System.out.println(adsSeller);
            }
        }
    }
}