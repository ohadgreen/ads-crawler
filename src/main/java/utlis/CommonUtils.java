package utlis;

import model.AdsSeller;
import model.Site;

public class CommonUtils {
    public static AdsSeller parseTextLineToAdsSellerObj(String line) {
        if (line.contains("#")) {
            line = line.substring(0, line.indexOf('#'));
        }

        try{
            if (line.length() < 2) {
                return null;
            }

            AdsSeller adsSeller = new AdsSeller();
            String[] sellerValues = line.split(",");

            if(sellerValues.length > 0 && sellerValues[0].trim().length() > 0) {
                adsSeller.setExchangeDomain(sellerValues[0].trim().toLowerCase());
            }
            if(sellerValues.length > 1 && sellerValues[1].trim().length() > 0) {
                adsSeller.setSellerAccountId(sellerValues[1].trim().toLowerCase());
            }
            if(sellerValues.length > 2 && sellerValues[2].trim().length() > 0 && sellerValues[2].trim().length() < 10) {
                adsSeller.setPaymentsType(sellerValues[2].trim().toUpperCase());
            }
            if(sellerValues.length > 3 && sellerValues[3].trim().length() > 0) {
                adsSeller.setTagId(sellerValues[3].trim());
            }
            else {
                adsSeller.setTagId("<N/A>");
            }
            if (adsSeller.getSellerAccountId() != null && adsSeller.getPaymentsType() != null){
                return adsSeller;
            }
            else
                return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Site parseLineToSiteObj(String line) {
        try {
            Site site = new Site();
            if (line.length() > 0 && line.contains(",")) {

                String[] siteValues = line.split(",");
                if (siteValues.length > 0 && siteValues[0].trim().length() > 0) {
                    site.setPublisherId(Integer.valueOf(siteValues[0].trim()));
                }
                if (siteValues.length > 1 && siteValues[1].trim().length() > 0) {
                    site.setSiteIndex(Integer.valueOf(siteValues[1].trim()));
                }
                if (siteValues.length > 2 && siteValues[2].trim().length() > 0) {
                    site.setSiteUrl(siteValues[2].trim());
                }
            }
            return site;
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatUrlToAdsCrawl(String rawSiteUrl) {
        if (rawSiteUrl.contains("http://")) {
            rawSiteUrl = rawSiteUrl.replace("http://", "https://");
        }
        return rawSiteUrl + "/ads.txt";
    }
}
