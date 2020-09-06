package app.update_site_ads;

import db.MySqlConnection;
import model.AdsSeller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.Properties;
import java.util.Set;

public class UpdateSiteAdsToMySqlTable implements UpdateSiteAds{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private Integer siteId;
    private Set<AdsSeller> adsSellerSet;
    private String insertStatement;
    private Properties properties;

    public UpdateSiteAdsToMySqlTable(Integer siteId, Set<AdsSeller> adsSellerSet, String insertStatement, Properties properties) {
        this.siteId = siteId;
        this.adsSellerSet = adsSellerSet;
        this.insertStatement = insertStatement;
        this.properties = properties;
    }

    @Override
    public void updateAdsList() {

        try(Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"))) {
//            Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));
            PreparedStatement ps;

            ps = connection.prepareStatement(this.insertStatement);

            int i = 0;
            for (AdsSeller adsSeller : adsSellerSet) {
                if (adsSeller.getSellerAccountId() == null) {
                    logger.debug("no seller account " + this.siteId + " - " + adsSeller.toString());
                }

                ps.setInt(1, siteId);
                ps.setString(2, adsSeller.getExchangeDomain());
                ps.setString(3, adsSeller.getSellerAccountId());
                ps.setString(4, adsSeller.getPaymentsType());
                ps.setString(5, adsSeller.getTagId());

                ps.addBatch();
                if(i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
//            logger.debug("updated to db: " + ps.getUpdateCount());
//            ps.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
