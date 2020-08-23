package com.likeview.csm.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.ApiResponseWithoutResData;
import com.likeview.csm.R;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.aswdc.autocadetutorial.adapter.VideoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavAddCoustomerFragment extends Fragment implements View.OnClickListener{
    TextView textUserName ,textPhoneNumber,textEmail,textCity,textState,textCountry,textPincode;
    ImageView textEdit;
    CircleImageView imgUserProfilePhoto;
    Button btnSubmit;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    Context context;
//    SharedPrefManager sfm = SharedPrefManager.getInstance(getActivity());
//    ProfileDetail pd = sfm.getUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_nav_add_coustomer, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        // Toolbar

        TextView toolbartext;
        toolbar=  view.findViewById(R.id.toolbar);
        toolbartext=  view.findViewById(R.id.toolbartext);
        toolbartext.setText( "Add Coustomer" );
        btnSubmit = view.findViewById( R.id.btnSubmit );
        btnSubmit.setOnClickListener( this );

        Log.d( "Dk::3", "Hello" );
    }

    private void userLogin() {

//        String email = editTextEmail.getText().toString().trim();
        String Name = "Darshan Kasundra";

//        if (email.isEmpty()) {
//            editTextEmail.setError( "Email is required" );
//            editTextEmail.requestFocus();
//            return;
//        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }

//        if (password.isEmpty()) {
//            editTextPassword.setError( "Password required" );
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        if (password.length() < 8) {
//            editTextPassword.setError( "Password should be atleast 8 character long" );
//            editTextPassword.requestFocus();
//            return;
//        }
        Log.d( "Dk::2", "Hello" );

        Api api = RetrofitClient.getApi().create( Api.class );
        Call<ApiResponseWithoutResData> call = api.getAddCoustomer( 1,"Darshan Kasundra" );

        Log.d( "Dk::2", "Hello" );

        call.enqueue( new Callback<ApiResponseWithoutResData>() {
            @Override
            public void onResponse(Call<ApiResponseWithoutResData> call, Response<ApiResponseWithoutResData> response) {
                ApiResponseWithoutResData apiResponseWithoutResData = response.body();
                Log.d( "Dk::1", "" + apiResponseWithoutResData.getResMessage() );

                if (response.body().getResCode() == 1) {
                    Log.d( "Dk", "" + apiResponseWithoutResData.getResMessage() );
                    Toast.makeText( getActivity(), apiResponseWithoutResData.getResMessage(), Toast.LENGTH_LONG ).show();
//                    Intent intent = new Intent( LoginActivity.this, MainNavActivity.class );
//                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( intent );
                } else {
                    Toast.makeText( getActivity(), apiResponseWithoutResData.getResMessage(), Toast.LENGTH_LONG ).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponseWithoutResData> call, Throwable t) {
                Log.d( "Login", "" + t.getLocalizedMessage() );
                Toast.makeText( getActivity() , "Hello", Toast.LENGTH_LONG ).show();
            }
        } );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                userLogin();
                break;

        }
    }
}
