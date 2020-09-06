package app;

import app.crawler.CrawlSiteImpl;
import model.AdsSeller;
import model.Site;
import org.junit.Test;

import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class CrawlSiteTest {

    @Test
    public void readUrlForSellerInfoSimpleTest() {
        String sheredais = "https://sharedais.com";
        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(sheredais), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void testUrlProblem() {
        String panolian = "http://panolian.com";
        String newCentral = "https://thenewscentral.org";

        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(panolian), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

        sellerLineSet.stream().limit(10).forEach(System.out::println);
    }

    @Test
    public void readUrlForSellerInfoWrongUrl() {
        String falseUrl = "xyz";
        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(falseUrl), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

        assertEquals(0, sellerLineSet.size());
    }

    @Test
    public void testLongPaymentType() {
        String smoking = "https://www.smokingmeatforums.com";
//        google.com, pub-4968145218643279, DIRECT  f08c47fec0942fa0
        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(smoking), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

        for (AdsSeller adsSeller : sellerLineSet) {
            if (adsSeller.getPaymentsType().length() > 10) {
                System.out.println(adsSeller);
            }
        }
    }
    @Test
    public void testDuplicate() {
        String smoking = "https://www.smokingmeatforums.com";
        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(smoking), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

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
        CrawlSiteImpl crawlSiteImpl = new CrawlSiteImpl(setSiteUrl(smoking), new Properties());
        Set<AdsSeller> sellerLineSet = crawlSiteImpl.readUrlForSellerInfo();

        for (AdsSeller adsSeller : sellerLineSet) {
            if (adsSeller.getSellerAccountId() == null) {
                System.out.println(adsSeller);
            }
        }
    }

    private Site setSiteUrl(String url) {
        Site testSite = new Site();
        testSite.setSiteUrl(url);
        return testSite;
    }
}