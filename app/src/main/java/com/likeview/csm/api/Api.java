package com.likeview.csm.api;



import com.google.android.material.textfield.TextInputEditText;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

//    @FormUrlEncoded
//    @POST("CheckMailExists")
//    Call<ApiResponseWhitoutResData> getCheckMailExists(
//            @Field("email") String email
//    );
//
//    @FormUrlEncoded
//    @POST("CheckMoileExists")
//    Call<ApiResponseWhitoutResData> getCheckMoileExists(
//            @Field("mobile") String mobile
//    );
//
//    @FormUrlEncoded
//    @POST("CheckUsernameExists")
//    Call<ApiResponseWhitoutResData> CheckUsernameExists(
//            @Field("user_name") String UsreName
//    );
//    @FormUrlEncoded
//    @POST("getOtp")
//    Call<ApiResponse> getOtp(
//            @Field("email") String email
//    );
//
//    @FormUrlEncoded
//    @POST("VerifyOtp")
//    Call<ApiResponseWhitoutResData> VerifyOtp(
//            @Field("user_id") int UserId,
//            @Field("otp") int OTP,
//            @Field("otp_for") String OtpFor
//    );

//
//
//
//
//
//    @FormUrlEncoded
//    @POST("login")
//    Call<ApiResponse> login(@Field("email_or_phones") String email, @Field("password") String password);
//@FormUrlEncoded

    @POST("listAllclient")
    Call<ApiResponse> getAllclientlists();

    @FormUrlEncoded
    @POST("saveCoustomer")
    Call<ApiResponseWithoutResData> saveButtonclientlists(
            @Field( "client_id" ) int clientId
    );
    @FormUrlEncoded
    @POST("listClient")
    Call<ApiResponse> getlistclientlists(
            @Field( "type_id" ) int typeId
    );
    @FormUrlEncoded
    @POST("detailClient")
    Call<ApiResponse> getdetailClient(
            @Field( "client_id" ) int clientId
    );
    @POST("savedClient")
    Call<ApiResponse> getsaveclientlists();

    @FormUrlEncoded
    @POST("addCoustomer")
    Call<ApiResponseWithoutResData> getAddCoustomer(
            @Field("type_id") String TypeId,
            @Field("firm_name") String firm_name,
            @Field("personal_name") String personal_name,
            @Field("address") String address,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country,
            @Field("email") String email,
            @Field("website") String website,
            @Field("mobile_no") String mobile_no,
            @Field("wp_no") String wp_no,
            @Field("req_size") String req_size,
            @Field("qty") String qty,
            @Field("payment_type") String payment_type,
            @Field("credit_time") String credit_time,
            @Field("dealing_with") String dealing_with,
            @Field("dealing_firm") String dealing_firm,
            @Field("dealing_since") String dealing_since,
            @Field("notifaction_date") String notifaction_date,
            @Field("communication") String communication

    );

//    @FormUrlEncoded
//    @POST("eventDetail")
//    Call<ApiResponse> geteventDetail(
//            @Field("event_id") int eventId
//    );
//
//
//
//    @FormUrlEncoded
//    @POST("getUserFiles")
//    Call<ApiResponse> getUserFiles(
//            @Field("user_id") int UserID
//    );
//
//    @FormUrlEncoded
//    @POST("addToMapping")
//    Call<ApiResponseWhitoutResData> getJoinUserInEvent(
//            @Field("event_id") int eventId,
//            @Field("user_id") int userId
//
//    );
//
//    @Multipart
//    @POST("uploadFile")
//    Call<ApiResponseWhitoutResData>uplodeFile(
//            @Part("user_id") RequestBody UserID,
//            @Part("event_id") RequestBody EventID,
//            @Part MultipartBody.Part main_file,
//            @Part MultipartBody.Part sub_file
//
//    );
////    @Multipart
////    @POST("addSubFile")
////    Call<ApiResponseWhitoutResData>uplodeFile(
////            @Part("user_id") RequestBody UserID,
////            @Part("event_id") RequestBody EventID,
////            @Part MultipartBody.Part sub_file
////
////
////    );
//
//    @FormUrlEncoded
//    @POST("editUser")
//    Call<ApiResponse> EditeUser(
//            @Field("user_id") int user_id,
//            @Field("name") String name,
//            @Field("mobile") String mobile,
//            @Field("email") String email,
//            @Field("city") String city
//
//    );
//
//    @FormUrlEncoded
//    @POST("addVote")
//    Call<ApiResponse> addVote(
//            @Field("voter_id") String voterID,
//            @Field("to_vote_id") String ToVoteID,
//            @Field("event_id") String EventID,
//            @Field("vote") int Vote
//    );
//
//
//    @FormUrlEncoded
//    @POST("listOfUserdetailOfEvent")
//    Call<ApiResponse> getlistOfUserdetailOfEvent(
//            @Field("event_id") int EventID,
//            @Field("user_id") int user_id
//    );
//    @POST("allSubFiles")
//    Call<ApiResponse> getallsubfile();
//
//    @POST("listOfAllWinner")
//    Call<ApiResponse> getlistOfAllWinner();
//
//    @FormUrlEncoded
//    @POST("userWinningList")
//    Call<ApiResponse> getuserWinningList(
//            @Field("user_id") int user_id
//    );
}
