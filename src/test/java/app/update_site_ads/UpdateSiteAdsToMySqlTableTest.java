package app.update_site_ads;

import db.MySqlConnection;
import model.AdsSeller;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateSiteAdsToMySqlTableTest {
    Properties properties = new Properties();

    @Test
    public void updateAdsListSmokeTest() {

        loadProperties();
        Set<AdsSeller> adsSellerSet = adsSellerInit();
        Integer siteId = 999;
        String insertStatement = "insert into sites_ads (site_id, exchange_domain, seller_account, payment_type, tag_id)" +
                " values (?, ?, ?, ?, ?)";

        UpdateSiteAds updateToDb = new UpdateSiteAdsToMySqlTable(siteId, adsSellerSet, insertStatement, properties);
        updateToDb.updateAdsList();

    }

    private Set<AdsSeller> adsSellerInit() {
        Set<AdsSeller> adsSellerSet = new HashSet<>();
        AdsSeller as1 = new AdsSeller();
        as1.setExchangeDomain("www.1234");
        as1.setSellerAccountId("1111");
        as1.setPaymentsType("DIRECT");
        as1.setTagId("ffffff");

        AdsSeller as2 = new AdsSeller();
        as2.setExchangeDomain("www.google.com");
        as2.setSellerAccountId("2222");
        as2.setPaymentsType("DIRECT");
        as2.setTagId("gggggg");

        adsSellerSet.add(as1);
        adsSellerSet.add(as2);
        return adsSellerSet;
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
}