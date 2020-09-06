package app.update_sites;

import db.MySqlConnection;
import model.Site;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

public class UpdateSitesToMySqlTable implements UpdateSiteList {
    private Set<Site> siteSet;
    private Properties properties;

    public UpdateSitesToMySqlTable(Set<Site> siteSet, Properties properties) {
        this.siteSet = siteSet;
        this.properties = properties;
    }

    @Override
    public void updateSiteList() {

        try {
            Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));
            PreparedStatement ps = null;
            String insertStatement = "insert into sites (publisher_id, site_index, site_url)" +
                    " values (?, ?, ?)";

            ps = connection.prepareStatement(insertStatement);

            int i = 0;
            for (Site site : siteSet) {
                ps.setInt(1, site.getPublisherId());
                ps.setInt(2, site.getSiteIndex());
                ps.setString(3, site.getSiteUrl());

                ps.addBatch();
                if(i % 200 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
