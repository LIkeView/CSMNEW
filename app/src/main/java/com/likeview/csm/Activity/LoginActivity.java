package com.likeview.csm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.MainActivity;
import com.likeview.csm.R;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;
import com.likeview.csm.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputEmail,inputPass;
    Button login;
    private SharedPrefManager sfm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail =(EditText)findViewById(R.id.inputEmail);
        inputPass =(EditText)findViewById(R.id.inputPassword);
        login=(Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(this);

    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance( this ).isLoggedIn()) {
            Intent intent = new Intent( this, MainActivity.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
            finish();
        }
    }

    private void userLogin() {

        String email = inputEmail.getText().toString().trim();
        String password = inputPass.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError( "Email is required" );
            inputEmail.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }

        if (password.isEmpty()) {
            inputPass.setError( "Password required" );
            inputPass.requestFocus();
            return;
        }

        if (password.length() < 8) {
            inputPass.setError( "Password should be atleast 8 character long" );
            inputPass.requestFocus();
            return;
        }

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponse> call = api.login( email, password );

        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.body().getResCode() == 1) {
                    Log.d( "Dk", "" + apiResponse.getResMessage() );
                    Toast.makeText( LoginActivity.this, apiResponse.getResMessage(), Toast.LENGTH_LONG ).show();
                    sfm = SharedPrefManager.getInstance( getApplicationContext() );
                    sfm.saveUser( apiResponse.getResData().getProfileDetails().get( 0 ) );

                    Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );
                } else {
                    Toast.makeText( LoginActivity.this, apiResponse.getResMessage(), Toast.LENGTH_LONG ).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d( "Login", "" + t.getLocalizedMessage() );

                Toast.makeText( LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG ).show();

            }
        } );

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }
}