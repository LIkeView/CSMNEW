package com.likeview.csm.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.ApiResponse.Model.ProfileDetailModel;
import com.likeview.csm.Connectivity.CheckNetwork;
import com.likeview.csm.R;
import com.likeview.csm.adapter.NavSaveAdapter;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;
import com.likeview.csm.storage.SharedPrefManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.aswdc.autocadetutorial.adapter.VideoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavProfileFragment extends Fragment {
    Context context;
    TextView textPersionName,textEmail,textPhone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_nav_profile, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        textPersionName = view.findViewById( R.id.textPersionName );
        textEmail = view.findViewById( R.id.textEmail );
        textPhone = view.findViewById( R.id.textPhone );

        SharedPrefManager sfm = SharedPrefManager.getInstance( context );
        ProfileDetailModel pd = sfm.getUser();
        Log.d( "pd",""+pd.getName() );
        textPersionName.setText( pd.getName() );
        textEmail.setText( pd.getEmail() );
        textPhone.setText( pd.getMobile() );
    }


}
