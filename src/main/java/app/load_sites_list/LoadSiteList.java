package app.load_sites_list;

import model.Site;

import java.util.Properties;
import java.util.Set;

public interface LoadSiteList {
    Set<Site> getSiteSet(Properties properties);
}
