package app.update_sites;

import app.load_sites_list.LoadSiteList;
import app.load_sites_list.LoadSitesFromFile;
import db.MySqlConnection;
import model.Site;
import org.junit.Test;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateSitesToMySqlTableTest {

    @Test
    public void updateSiteListSmokeTest() {
        Set<Site> siteSet = getSites(50);

        UpdateSiteList updateSiteList = new UpdateSitesToMySqlTable();
        updateSiteList.updateSiteList(setConnection(), siteSet);
    }

    private Set<Site> getSites(Integer limit) {
        LoadSiteList loadSiteList = new LoadSitesFromFile();
        Set<Site> allSitesFromFile = loadSiteList.getSiteSet();

        Set<Site> limitedList = new HashSet<>();
        allSitesFromFile.stream().limit(limit).forEach(s -> limitedList.add(s));
        return limitedList;
    }

    private Connection setConnection() {
        return MySqlConnection.getConnection("jdbc:mysql://localhost:3306/infolinks?rewriteBatchedStatements=true", "root", "covid19");
    }
}