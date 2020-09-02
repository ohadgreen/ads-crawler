package utlis;

import model.AdsSeller;
import model.Site;

public class CommonUtils {
    public static AdsSeller parseTextLineToAdsSellerObj(String line) {
        try{
            if (line.length() < 2) {
                return null;
            }
            if (line.charAt(0) == '#') {
                return null;
            }

            AdsSeller adsSeller = new AdsSeller();
            String[] sellerValues = line.split(",");

            if(sellerValues.length > 0 && sellerValues[0].trim().length() > 0) {
                adsSeller.setExchangeDomain(sellerValues[0].trim());
            }
            if(sellerValues.length > 1 && sellerValues[1].trim().length() > 0) {
                adsSeller.setSellerAccountId(sellerValues[1].trim());
            }
            if(sellerValues.length > 2 && sellerValues[2].trim().length() > 0) {
                adsSeller.setPaymentsType(sellerValues[2].trim());
            }
            if(sellerValues.length > 3 && sellerValues[3].trim().length() > 0) {
                adsSeller.setTagId(sellerValues[3].trim());
            }
            return adsSeller;
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
}
