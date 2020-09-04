package app.load_sites_list;

import model.Site;
import utlis.CommonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class LoadSitesFromFile implements LoadSiteList{
    @Override
    public Set<Site> getSiteSet(Properties properties) {
        String fileName = "sites_list.csv";
        Set<Site> siteSet = new HashSet<>();
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.lines().forEach(line -> {
                Site site = CommonUtils.parseLineToSiteObj(line);
                if (site != null) {
                    siteSet.add(site);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return siteSet;
        }
        return siteSet;
    }
}
