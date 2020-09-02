package app.load_master;

import model.AdsSeller;
import utlis.CommonUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LoadMasterAdsFromFile implements LoadMasterAds{
    @Override
    public Set<AdsSeller> getMasterSellerAdsSet() {
        String fileName = "infolinks_master-ads.txt";
        Set<AdsSeller> masterAdsSet = new HashSet<>();
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.lines().forEach(line -> {
                AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
                if (adsSeller != null) {
                    masterAdsSet.add(adsSeller);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return masterAdsSet;
        }
        return masterAdsSet;
    }
}
