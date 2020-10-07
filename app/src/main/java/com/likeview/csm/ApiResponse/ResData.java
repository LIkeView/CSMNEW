package com.likeview.csm.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.likeview.csm.ApiResponse.Model.ClientDetailsModel;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.ApiResponse.Model.ProfileDetailModel;

import java.util.ArrayList;
import java.util.List;

public class ResData {

//    List Client in ResData Model



// save cilent data
    @SerializedName("list_client")
    @Expose
    private ArrayList<ListClientModel> listClient = null;

    public ArrayList<ListClientModel> getListClient() {
        return listClient;
    }

    public void setListClient(ArrayList<ListClientModel> listClient) {
        this.listClient = listClient;
    }

// Deatail Client
    @SerializedName("client_details")
    @Expose
    private ArrayList<ClientDetailsModel> clientdetails = null;

    public ArrayList<ClientDetailsModel> getClientdetails(int clientID) {
        return clientdetails;
    }

    public void setClientdetails(ArrayList<ClientDetailsModel> clientdetails) {
        this.clientdetails = clientdetails;
    }

    @SerializedName("profile_details")
    @Expose
    private List<ProfileDetailModel> profileDetails = null;

    public List<ProfileDetailModel> getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(List<ProfileDetailModel> profileDetails) {
        this.profileDetails = profileDetails;
    }
}



