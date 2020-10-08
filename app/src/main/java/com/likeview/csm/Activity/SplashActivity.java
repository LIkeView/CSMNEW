package com.likeview.csm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.likeview.csm.MainActivity;
import com.likeview.csm.R;
import com.likeview.csm.storage.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        SharedPrefManager sfm = SharedPrefManager.getInstance(getApplicationContext());
//        if(sfm.isLoggedIn())
//        {
//            startActivity( new Intent( SplashActivity.this, MainActivity.class) );
//            finish();
//        }else
//        {
//            startActivity( new Intent( SplashActivity.this, LoginActivity.class) );
//            finish();
//        }
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                SharedPrefManager sfm = SharedPrefManager.getInstance(getApplicationContext());

                if(sfm.isLoggedIn())
        {
            startActivity( new Intent( SplashActivity.this, MainActivity.class) );
            finish();
        }else
        {
            startActivity( new Intent( SplashActivity.this, LoginActivity.class) );
            finish();
        }
            }
        }, 2000);
    }
}