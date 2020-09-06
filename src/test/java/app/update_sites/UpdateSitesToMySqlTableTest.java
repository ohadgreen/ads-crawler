package app.update_sites;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromFile;
import db.MySqlConnection;
import model.Site;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateSitesToMySqlTableTest {
    private Properties properties = new Properties();

    @Test
    public void updateSiteListSmokeTest() {
        loadProperties();
        Set<Site> siteSet = getSites(15000);

        UpdateSiteList updateSiteList = new UpdateSitesToMySqlTable(siteSet, properties);
        updateSiteList.updateSiteList();
    }

    private Set<Site> getSites(Integer limit) {
        LoadSiteList loadSiteList = new LoadSitesFromFile();
        Set<Site> allSitesFromFile = loadSiteList.getSiteSet(new Properties());

        Set<Site> limitedList = new HashSet<>();
        allSitesFromFile.stream().limit(limit).forEach(s -> limitedList.add(s));
        return limitedList;
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