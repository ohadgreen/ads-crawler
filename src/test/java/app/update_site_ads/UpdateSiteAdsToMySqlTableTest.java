package app.update_site_ads;

import db.MySqlConnection;
import model.AdsSeller;
import org.junit.Test;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateSiteAdsToMySqlTableTest {

    @Test
    public void updateAdsListSmokeTest() {

        Set<AdsSeller> adsSellerSet = adsSellerInit();
        Integer siteId = 999;

        UpdateSiteAds updateToDb = new UpdateSiteAdsToMySqlTable();
        updateToDb.updateAdsList(setConnection(), siteId, adsSellerSet);

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

    private Connection setConnection() {
        return MySqlConnection.getConnection("jdbc:mysql://localhost:3306/infolinks?rewriteBatchedStatements=true", "root", "covid19");
    }
}