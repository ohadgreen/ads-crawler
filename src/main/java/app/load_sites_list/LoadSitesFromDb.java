package app.load_sites_list;

import db.MySqlConnection;
import model.Site;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class LoadSitesFromDb implements LoadSiteList {
    @Override
    public Set<Site> getSiteSet(Properties properties) {

        Set<Site> siteSet = new HashSet<>();
        Connection connection = MySqlConnection.getConnection(properties.getProperty("DB_URL"), properties.getProperty("DB_USERNAME"), properties.getProperty("DB_PASSWORD"));
        try {
            String query = "select site_id, publisher_id, site_index, site_url from sites";
            Statement getAllSitesQuery = connection.createStatement();
            ResultSet resultSet = getAllSitesQuery.executeQuery(query);

            while (resultSet.next()) {
                Site site = new Site();
                site.setSiteId(resultSet.getInt("site_id"));
                site.setPublisherId(resultSet.getInt("publisher_id"));
                site.setSiteIndex(resultSet.getInt("site_index"));
                site.setSiteUrl(resultSet.getString("site_url"));
                siteSet.add(site);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return siteSet;
    }
}
