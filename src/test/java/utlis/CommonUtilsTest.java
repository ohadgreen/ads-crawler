package utlis;

import model.AdsSeller;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonUtilsTest {

    @Test
    public void parseTextLineToAdsSellerObjAllArgsTest() {
        String line = "spotx.tv, 211156, RESELLER, 7842df1d2fe2db34";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertEquals("spotx.tv", adsSeller.getExchangeDomain());
        assertEquals("211156", adsSeller.getSellerAccountId());
        assertEquals("RESELLER", adsSeller.getPaymentsType());
        assertEquals("7842df1d2fe2db34", adsSeller.getTagId());
    }

    @Test
    public void parseTextLineToAdsSellerObjTwoArgsTest() {
        String line = "spotx.tv, 211156, direct";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertEquals("spotx.tv", adsSeller.getExchangeDomain());
        assertEquals("211156", adsSeller.getSellerAccountId());
        System.out.println(adsSeller);
        assertEquals("DIRECT", adsSeller.getPaymentsType());
        assertEquals("<N/A>", adsSeller.getTagId());
    }

    @Test
    public void parseLineWithComment() {
        String line = "sovrn.com, 237754, DIRECT, fafdf38b16bf6b2b # Sovrn Hbidding";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertEquals("sovrn.com", adsSeller.getExchangeDomain());
        assertEquals("237754", adsSeller.getSellerAccountId());
        assertEquals("DIRECT", adsSeller.getPaymentsType());
        assertEquals("fafdf38b16bf6b2b", adsSeller.getTagId());
    }

    @Test
    public void parseEmptyLine_shouldReturnNull() {
        String line = " ";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertNull(adsSeller);
    }

    @Test
    public void parseCommentLine_shouldReturnNull() {
        String line = "#Ads";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertNull(adsSeller);
    }

    @Test
    public void formatUrlTest() {
        String rawUrl = "http://www.3daimtrainer.com";
        String crawlUrl = CommonUtils.formatUrlToAdsCrawl(rawUrl);
        assertEquals("https://www.3daimtrainer.com/ads.txt", crawlUrl);
    }

    @Test
    public void formatUrlHttpsTest() {
        String rawUrl = "https://www.3daimtrainer.com";
        String crawlUrl = CommonUtils.formatUrlToAdsCrawl(rawUrl);
        assertEquals("https://www.3daimtrainer.com/ads.txt", crawlUrl);
    }
}