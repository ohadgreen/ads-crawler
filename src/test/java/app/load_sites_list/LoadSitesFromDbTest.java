package app.load_sites_list;

import db.MySqlConnection;
import model.Site;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class LoadSitesFromDbTest {

    private Properties properties = new Properties();
    @Test
    public void getSiteSetSmokeTest() {
        loadProperties();
        LoadSiteList loadSiteList = new LoadSitesFromDb();
        Set<Site> siteSet = loadSiteList.getSiteSet(properties);
        siteSet.stream().limit(10).forEach(System.out::println);

    }

    private void loadProperties() {
        String fileName = "application.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(fileName)) {
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}