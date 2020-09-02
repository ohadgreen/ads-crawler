package app.crawler;

import model.AdsSeller;
import utlis.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class CrawlSite {

    public Set<AdsSeller> readUrlForSellerInfo(String url) throws IOException {
        URL myUrl = new URL(url);
        Set<AdsSeller> adsSellerSet = new HashSet<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            Integer responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                reader.lines().forEach(line -> {
                    AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
                    if (adsSeller != null) {
                        adsSellerSet.add(adsSeller);
                    }
                });
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return adsSellerSet;
        }

        return adsSellerSet;
    }
}
