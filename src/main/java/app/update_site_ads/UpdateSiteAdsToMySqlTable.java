package app.update_site_ads;

import model.AdsSeller;

import java.sql.*;
import java.util.Set;

public class UpdateSiteAdsToMySqlTable implements UpdateSiteAds{

    @Override
    public void updateAdsList(Connection connection, Integer siteId, Set<AdsSeller> adsSellerSet) {

        try {
            PreparedStatement ps = null;
            String insertStatement = "insert into sites_ads (site_id, exchange_domain, seller_account, payment_type, tag_id)" +
                    " values (?, ?, ?, ?, ?)";

            ps = connection.prepareStatement(insertStatement);

            int i = 0;
            for (AdsSeller adsSeller : adsSellerSet) {
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
