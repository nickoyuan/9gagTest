package com.a9gag.nick.testapplication;

public class GagsData {

    private String payloadId = null;
    private String payloadTitleList = null;
    private String payloadTypeList = null;
    private String payloadUrlList = null;
    private String urlWidth = null;
    private String urlHeight = null;
    private String payloadNSFW = null;

    public GagsData() {

    }
    public GagsData(
        String payloadId, String payloadTitleList,
        String payloadTypeList,
        String payloadUrlList,
        String urlWidth, String urlHeight,
        String payloadNSFW
        )
    {
        this.payloadId=payloadId;
        this.payloadTitleList=payloadTitleList;
        this.payloadTypeList=payloadTypeList;
        this.payloadUrlList=payloadUrlList;
        this.urlWidth=urlWidth;
        this.urlHeight=urlHeight;
        this.payloadNSFW=payloadNSFW;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getPayloadTitle() {
        return payloadTitleList;
    }

    public void setPayloadTitle(String payloadTitleList) {
        this.payloadTitleList = payloadTitleList;
    }

    public String getPayloadType() {
        return payloadTypeList;
    }

    public void setPayloadType(String payloadTypeList) {
        this.payloadTypeList = payloadTypeList;
    }

    public String getPayloadUrl() {
        return payloadUrlList;
    }

    public void setPayloadUrl(String payloadUrlList) {
        this.payloadUrlList = payloadUrlList;
    }

    public String getUrlWidth() {
        return urlWidth;
    }

    public void setUrlWidth(String urlWidth) {
        this.urlWidth = urlWidth;
    }

    public String getUrlHeight() {
        return urlHeight;
    }

    public void setUrlHeight(String urlHeight) {
        this.urlHeight = urlHeight;
    }

    public String getPayloadNSFW() {
        return payloadNSFW;
    }

    public void setPayloadNSFW(String payloadNSFW) {
        this.payloadNSFW = payloadNSFW;
    }
}