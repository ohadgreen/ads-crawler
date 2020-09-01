package model;

import java.util.Objects;

public class AdsSeller {
    private String exchangeDomain;
    private String sellerAccountId;
    private String paymentsType;
    private String tagId;

    public AdsSeller() {
    }

    public String getExchangeDomain() {
        return exchangeDomain;
    }

    public void setExchangeDomain(String exchangeDomain) {
        this.exchangeDomain = exchangeDomain;
    }

    public String getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(String sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public String getPaymentsType() {
        return paymentsType;
    }

    public void setPaymentsType(String paymentsType) {
        this.paymentsType = paymentsType;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "AdsSeller{" +
                "exchangeDomain='" + exchangeDomain + '\'' +
                ", sellerAccountId='" + sellerAccountId + '\'' +
                ", paymentsType='" + paymentsType + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsSeller adsSeller = (AdsSeller) o;
        return Objects.equals(exchangeDomain, adsSeller.exchangeDomain) &&
                Objects.equals(sellerAccountId, adsSeller.sellerAccountId) &&
                Objects.equals(paymentsType, adsSeller.paymentsType) &&
                Objects.equals(tagId, adsSeller.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeDomain, sellerAccountId, paymentsType, tagId);
    }
}
