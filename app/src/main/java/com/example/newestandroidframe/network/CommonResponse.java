package com.example.newestandroidframe.network;


public class CommonResponse {
    private String retCode;
    private String retInfo;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
            "retCode='" + retCode + '\'' +
            ", retInfo='" + retInfo + '\'' +
            '}';
    }
}
