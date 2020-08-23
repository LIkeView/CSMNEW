package com.likeview.csm.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseWithoutResData {

    @SerializedName("res_code")
    @Expose
    private Integer resCode;
    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

}

