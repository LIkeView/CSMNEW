package com.likeview.csm.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.Connectivity.CheckNetwork;
import com.likeview.csm.R;
import com.likeview.csm.adapter.HomeTabAdapter;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeTabFragment extends Fragment {
    RecyclerView rcvallSubFileList;
    Context context;
    SwipeRefreshLayout swipeHome;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView navUserName;
    ImageView backButton;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int index;
    public HomeTabFragment(int i) {
        this.index = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home_tab, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        swipeHome = view.findViewById( R.id.swipeHome );
        toolbar = view.findViewById( R.id.toolbar );
        rcvallSubFileList = view.findViewById( R.id.rcvallSubFileList );
        navUserName  = view.findViewById( R.id.navUserName );

        manager = new LinearLayoutManager( getActivity() );
        rcvallSubFileList.setLayoutManager( manager );

        if(CheckNetwork.isInternetAvailable(getActivity())) //returns true if internet available
        {
            initReference();
            //do something. loadwebview.
        }
        else
        {
            Toast.makeText(getActivity(),"No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }
    void initReference() {
//        set Progress Dialog
        rcvallSubFileList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
                }
            }
        });
            getData();

//        navUserName.setText("pd.getName() " );

//        Set swipe Refrish
        swipeHome.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                new Handler(  ).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        swipeHome.setRefreshing( false );
                    }
                } ,400);
            }
        } );


    }

    void getData(){
        ProgressDialog progress = new ProgressDialog( getActivity() );
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        Api api = RetrofitClient.getApi().create(Api.class);
        Call<ApiResponse> call = api.getlistclientlists(index);
        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getResCode() == 1) {
                    progress.dismiss();
                    ArrayList<ListClientModel> subfilesWithUserDetailHistories = (ArrayList<ListClientModel>) response.body().getResData().getListClient();
                    rcvallSubFileList.setAdapter(new HomeTabAdapter(getActivity(), subfilesWithUserDetailHistories));
                }
                else
                {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }
//                progress.dismiss();

            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Z",""+t.getLocalizedMessage());
                Toast.makeText( getActivity(), "Client is not added", Toast.LENGTH_LONG ).show();
                progress.dismiss();

            }
        } );

    }
}