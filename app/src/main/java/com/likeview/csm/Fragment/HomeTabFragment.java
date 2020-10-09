package com.likeview.csm.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.ApiResponse.Model.ProfileDetailModel;
import com.likeview.csm.Connectivity.CheckNetwork;
import com.likeview.csm.R;
import com.likeview.csm.adapter.HomeTabAdapter;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;
import com.likeview.csm.storage.SharedPrefManager;

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
    SearchManager searchManager;
    ImageView backButton;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    SearchView searchViewhome;
    ArrayList<ListClientModel> subfilesWithUserDetailHistories;
    int currentItems, totalItems, scrollOutItems;
    int index;
    private ShimmerFrameLayout mShimmerViewContainer;
    HomeTabAdapter homeTabAdapter;
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
        searchViewhome = view.findViewById(R.id.searchViewhome);
        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_home);


        manager = new LinearLayoutManager( getActivity() );
        rcvallSubFileList.setLayoutManager( manager );

        if(CheckNetwork.isInternetAvailable(getActivity())) //returns true if internet available
        {
            initReference();
            //do something. loadwebview.
        }
        else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
//                Log.d(SyncStateContract.Constants.TAG, "Show Dialog: " + e.getMessage());
            }
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
//        ProgressDialog progress = new ProgressDialog( getActivity() );
//        progress.setTitle("Loading");
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false);
//        progress.show();

        SharedPrefManager sfm = SharedPrefManager.getInstance( context );
        ProfileDetailModel pd = sfm.getUser();

        Api api = RetrofitClient.getApi().create(Api.class);
        Call<ApiResponse> call = api.getlistclientlists(index,pd.getUserId());
        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getResCode() == 1) {
//                    progress.dismiss();
                    subfilesWithUserDetailHistories = (ArrayList<ListClientModel>) response.body().getResData().getListClient();
                    homeTabAdapter = new HomeTabAdapter(getActivity(), subfilesWithUserDetailHistories);
                    rcvallSubFileList.setAdapter(homeTabAdapter);
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
                else
                {
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
//                    progress.dismiss();
                }
//                progress.dismiss();

            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        } );

        searchViewhome.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchViewhome.setIconifiedByDefault(false);

        searchViewhome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if(subfilesWithUserDetailHistories.contains(s)){
                homeTabAdapter.getFilter().filter(s);
//                }else{
//                    Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                homeTabAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}