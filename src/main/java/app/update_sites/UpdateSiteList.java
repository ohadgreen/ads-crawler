package app.update_sites;

import model.Site;

import java.sql.Connection;
import java.util.Set;

public interface UpdateSiteList {
    void updateSiteList(Connection connection, Set<Site> siteSet);
}
