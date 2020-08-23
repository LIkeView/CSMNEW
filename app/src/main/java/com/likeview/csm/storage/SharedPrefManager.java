package com.likeview.csm.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.likeview.csm.ApiResponse.Model.ProfileDetailModel;

//import net.simplifiedcoding.retrofitandroidtutorial.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(ProfileDetailModel user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("user_id", user.getUserId());
        editor.putString("name", user.getName());
        editor.putString("userName", user.getUserName());
        editor.putString("mobile", user.getMobile());
        editor.putString("email", user.getEmail());
        editor.putString("profilePic", user.getProfilePic());
        editor.putString("profilePicPath", user.getProfilePicPath());
        editor.putString("gender", user.getGender());
        editor.putString("webUrl", user.getWebUrl());
        editor.putString("companyName", user.getCompanyName());
        editor.putString("profession", user.getProfession());
        editor.putString("address", user.getAddress());
        editor.putString("city", user.getCity());
        editor.putString("state", user.getState());
        editor.putString("country", user.getCountry());
        editor.putString("pinCode", user.getPinCode());
        editor.putString("status", user.getState());
        editor.putString("createdDate", user.getCreatedDate());
        editor.putString("updatedDate", user.getUpdatedDate());
        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", 0) != 0;
    }

    public ProfileDetailModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ProfileDetailModel(
                sharedPreferences.getInt("user_id", 0),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("userName", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("profilePic", null),
                sharedPreferences.getString("profilePicPath", null),
                sharedPreferences.getString("gender", null),
                sharedPreferences.getString("webUrl", null),
                sharedPreferences.getString("companyName", null),
                sharedPreferences.getString("profession", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("city", null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString("country", null),
                sharedPreferences.getString("pinCode", null),
                sharedPreferences.getString("status", null),
                sharedPreferences.getString("createdDate", null),
                sharedPreferences.getString("updatedDate", null) );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
