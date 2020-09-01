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
        String line = "spotx.tv, 211156";
        AdsSeller adsSeller = CommonUtils.parseTextLineToAdsSellerObj(line);
        assertEquals("spotx.tv", adsSeller.getExchangeDomain());
        assertEquals("211156", adsSeller.getSellerAccountId());
        assertNull(adsSeller.getPaymentsType());
        assertNull(adsSeller.getTagId());
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
}