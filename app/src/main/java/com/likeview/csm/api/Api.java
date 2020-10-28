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


    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> login(@Field("email_or_phones") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("listAllclient")
    Call<ApiResponse> getAllclientlists(
            @Field( "user_id" ) int userID

    );

    @FormUrlEncoded
    @POST("saveCoustomer")
    Call<ApiResponseWithoutResData> saveButtonclientlists(
            @Field( "client_id" ) int clientId
    );
    @FormUrlEncoded
    @POST("listClient")
    Call<ApiResponse> getlistclientlists(
            @Field( "type_id" ) int typeId,
            @Field( "user_id" ) int userID
    );
    @FormUrlEncoded
    @POST("detailClient")
    Call<ApiResponse> getdetailClient(
            @Field( "client_id" ) int clientId
    );
    @FormUrlEncoded
    @POST("savedClient")
    Call<ApiResponse> getsaveclientlists(
            @Field("user_id") String UserID
    );

    @Multipart
    @POST("addCoustomer")
    Call<ApiResponseWithoutResData> getAddCoustomer(
            @Part("type_id") RequestBody TypeId,
            @Part("user_id") RequestBody user_id,
            @Part("firm_name") RequestBody firm_name,
            @Part("personal_name") RequestBody personal_name,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("country") RequestBody country,
            @Part("email") RequestBody email,
            @Part("website") RequestBody website,
            @Part("mobile_no") RequestBody mobile_no,
            @Part("wp_no") RequestBody wp_no,
            @Part MultipartBody.Part visiting_card_front,
            @Part MultipartBody.Part visiting_card_back,
            @Part MultipartBody.Part profile_pic,
            @Part("req_size") RequestBody req_size,
            @Part("qty") RequestBody qty,
            @Part("payment_type") RequestBody payment_type,
            @Part("credit_time") RequestBody credit_time,
            @Part("dealing_with") RequestBody dealing_with,
            @Part("dealing_firm") RequestBody dealing_firm,
            @Part("dealing_since") RequestBody dealing_since,
            @Part("notifaction_date") RequestBody notifaction_date,
            @Part("communication") RequestBody communication
    );

    @Multipart
    @POST("uploadFile")
    Call<ApiResponseWithoutResData>uplodeFile(
            @Part("client_id") RequestBody ClientID,
            @Part MultipartBody.Part visiting_card_front,
            @Part MultipartBody.Part visiting_card_back,
            @Part MultipartBody.Part profile_pic

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
