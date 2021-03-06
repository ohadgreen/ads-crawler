package app.load_master;

import model.AdsSeller;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class LoadMasterAdsFromFileTest {

    @Test
    public void getMasterSellerAdsSet() {
        LoadMasterAds loadMasterAds = new LoadMasterAdsFromFile();
        Set<AdsSeller> masterSellerAdsSet = loadMasterAds.getMasterSellerAdsSet();

//        masterSellerAdsSet.stream().forEach(System.out::println);
        assertTrue(masterSellerAdsSet.size() > 0);

        for (AdsSeller masterAd : masterSellerAdsSet) {
            System.out.println("insert into master_ads values (\"" + masterAd.getExchangeDomain() + "\", \"" + masterAd.getSellerAccountId() + "\", \"" + masterAd.getPaymentsType() + "\", \"" + masterAd.getTagId() + "\");");
        }
    }
}