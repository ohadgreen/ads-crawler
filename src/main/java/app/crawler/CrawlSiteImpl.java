package app.crawler;

import db.MySqlConnection;
import model.AdsSeller;
import model.Site;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class CrawlSiteImpl implements CrawlSite{

    private Site site;
    private Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public CrawlSiteImpl(Site site, Properties properties) {
        this.site = site;
        this.properties = properties;
    }

    @Override
    public Set<AdsSeller> readUrlForSellerInfo() {
        Set<AdsSeller> adsSellerSet = new HashSet<>();
        URL myUrl;
        String adsUrl = CommonUtils.formatUrlToAdsCrawl(this.site.getSiteUrl());
        try {
            myUrl = new URL(adsUrl);
        } catch (MalformedURLException badUrl) {
            updateSitesTableWithCrawlError("bad URL: " + adsUrl);
            return adsSellerSet;
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) myUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "AdsTxtCrawler/1.0");
            httpURLConnection.setRequestProperty("Accept", "text/plain");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);

            Integer responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
                reader.lines().forEach(line -> {
                    AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
                    if (adsSeller != null) {
                        adsSellerSet.add(adsSeller);
                    }
                });
            }
            else {
                logger.debug("site " + this.site.getSiteUrl() + "code: " + responseCode);
                updateSitesTableWithCrawlError("response code - " + responseCode.toString());
            }
            logger.debug("site " + this.site.getSiteId() + " : ads # - " + adsSellerSet.size());
        }
        catch (Exception e) {
            updateSitesTableWithCrawlError(e.getMessage());
            return adsSellerSet;
        }

        return adsSellerSet;
    }

    protected void updateSitesTableWithCrawlError(String errorMessage) {
        logger.debug("crawl error " + this.site.getSiteUrl() + "-" + errorMessage);

        PreparedStatement updateSite;
        String shortMessage = errorMessage.substring(0, Math.min(errorMessage.length(), 100));
        try(Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD")))
        {
            updateSite = connection.prepareStatement("UPDATE sites SET crawl_error = ? WHERE site_id = ?");
            updateSite.setString(1, shortMessage);
            updateSite.setInt(2, this.site.getSiteId());
            updateSite.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
