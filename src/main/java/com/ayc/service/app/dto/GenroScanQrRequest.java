package com.ayc.service.app.dto;

public class GenroScanQrRequest {
    private String uid;
    private String scanQrValue = ""; //生成二维码的字符串
    private String url;//二维码Logo头像,空不生成，default 就是公司logo
    private Integer qrSize;// 二维码尺寸正方形
    private Integer width;//宽度
    private Integer height;//高端
    private boolean needIoc = true;// 是否需要压缩Logo

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getScanQrValue() {
        return scanQrValue;
    }
    public void setScanQrValue(String scanQrValue) {
        this.scanQrValue = scanQrValue;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getQrSize() {
        return qrSize;
    }
    public void setQrSize(Integer qrSize) {
        this.qrSize = qrSize;
    }

    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean getNeedIoc() {
        return needIoc;
    }
    public void setNeedIoc(boolean needIoc) {
        this.needIoc = needIoc;
    }
}
