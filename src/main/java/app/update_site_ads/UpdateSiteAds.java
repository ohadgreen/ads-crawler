package app.update_site_ads;

import model.AdsSeller;

import java.sql.Connection;
import java.util.Set;

public interface UpdateSiteAds {
    void updateAdsList(Connection connection, Integer siteId, Set<AdsSeller> adsSellerSet);
}
