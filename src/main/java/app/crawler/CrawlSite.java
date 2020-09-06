package app.crawler;

import model.AdsSeller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utlis.CommonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class CrawlSite {

    // TODO: interface
    private String siteUrl;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public CrawlSite(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Set<AdsSeller> readUrlForSellerInfo() {
        Set<AdsSeller> adsSellerSet = new HashSet<>();
        URL myUrl;
        String adsUrl = CommonUtils.formatUrlToAdsCrawl(this.siteUrl);
        try {
            myUrl = new URL(adsUrl);
        } catch (MalformedURLException badUrl) {
            System.out.println("bad URL: " + adsUrl);
            return adsSellerSet;
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "AdsTxtCrawler/1.0");
            connection.setRequestProperty("Accept", "text/plain");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);

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
            else {
                logger.debug("site " + this.siteUrl + "code: " + responseCode);
            }

            logger.debug("site " + this.siteUrl + " : ads # - " + adsSellerSet.size());
        }
        catch (Exception e) {
            logger.debug("site " + this.siteUrl + e.getMessage());
            return adsSellerSet;
        }

        return adsSellerSet;
    }
}
