package app.load_sites_list;

import model.Site;
import org.junit.Test;

import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

public class LoadSitesFromFileTest {

    @Test
    public void getSiteSet() {
        Properties properties = new Properties();
        LoadSiteList loadSiteList = new LoadSitesFromFile();
        Set<Site> siteSet = loadSiteList.getSiteSet(properties);

        siteSet.stream().limit(10).forEach(System.out::println);
    }
}