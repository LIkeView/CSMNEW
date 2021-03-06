package com.likeview.csm.ApiResponse.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListClientModel {
    @SerializedName("client_id")
    @Expose
    private int clientId;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("visiting_card_front")
    @Expose
    private String visiting_card_front;
    @SerializedName("visiting_card_back")
    @Expose
    private String visiting_card_back;
    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;
    @SerializedName("firm_name")
    @Expose
    private String firmName;
    @SerializedName("personal_name")
    @Expose
    private String persionname;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;
    @SerializedName("wp_no")
    @Expose
    private String wp_no;
    @SerializedName("is_saved")
    @Expose
    private String is_saved;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String created_date;
    @SerializedName("updated_date")
    @Expose
    private String updated_date;
    @SerializedName("req_id")
    @Expose
    private String req_id;
    @SerializedName("req_size")
    @Expose
    private String req_size;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("credit_time")
    @Expose
    private String credit_time;
    @SerializedName("dealing_with")
    @Expose
    private String dealing_with;
    @SerializedName("dealing_firm")
    @Expose
    private String dealing_firm;
    @SerializedName("dealing_since")
    @Expose
    private String dealing_since;
    @SerializedName("communication")
    @Expose
    private String communication;
    @SerializedName("Status")
    @Expose
    private String Status;
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getVisiting_card_front() {
        return visiting_card_front;
    }

    public void setVisiting_card_front(String visiting_card_front) {
        this.visiting_card_front = visiting_card_front;
    }

    public String getVisiting_card_back() {
        return visiting_card_back;
    }

    public void setVisiting_card_back(String visiting_card_back) {
        this.visiting_card_back = visiting_card_back;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getPersionname() {
        return persionname;
    }

    public void setPersionname(String persionname) {
        this.persionname = persionname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getWp_no() {
        return wp_no;
    }

    public void setWp_no(String wp_no) {
        this.wp_no = wp_no;
    }

    public String getIs_saved() {
        return is_saved;
    }

    public void setIs_saved(String is_saved) {
        this.is_saved = is_saved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getReq_size() {
        return req_size;
    }

    public void setReq_size(String req_size) {
        this.req_size = req_size;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCredit_time() {
        return credit_time;
    }

    public void setCredit_time(String credit_time) {
        this.credit_time = credit_time;
    }

    public String getDealing_with() {
        return dealing_with;
    }

    public void setDealing_with(String dealing_with) {
        this.dealing_with = dealing_with;
    }

    public String getDealing_firm() {
        return dealing_firm;
    }

    public void setDealing_firm(String dealing_firm) {
        this.dealing_firm = dealing_firm;
    }

    public String getDealing_since() {
        return dealing_since;
    }

    public void setDealing_since(String dealing_since) {
        this.dealing_since = dealing_since;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }


}
