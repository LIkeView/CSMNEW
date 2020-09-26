package com.likeview.csm.Fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.Connectivity.CheckNetwork;
import com.likeview.csm.R;
import com.likeview.csm.adapter.HomeAllTabAdapter;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllHomeTabFragment extends Fragment {
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
    SearchView searchView;
    ArrayList<ListClientModel> subfilesWithUserDetailHistories;
    SearchManager searchManager;
    HomeAllTabAdapter homeAllTabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home_all_tab, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        swipeHome = view.findViewById( R.id.swipeHome );
        toolbar = view.findViewById( R.id.toolbar );
        rcvallSubFileList = view.findViewById( R.id.rcvallSubFileList );
        navUserName  = view.findViewById( R.id.navUserName );
        searchView = view.findViewById(R.id.searchView);
        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


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
        Call<ApiResponse> call = api.getAllclientlists();
        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getResCode() == 1) {
                    progress.dismiss();
                    subfilesWithUserDetailHistories = (ArrayList<ListClientModel>) response.body().getResData().getListClient();
                    homeAllTabAdapter = new HomeAllTabAdapter(getActivity(), subfilesWithUserDetailHistories);
                    rcvallSubFileList.setAdapter(homeAllTabAdapter);
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
                Toast.makeText( getActivity(), t.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
                progress.dismiss();

            }
        } );

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(subfilesWithUserDetailHistories.contains(s)){
                    homeAllTabAdapter.getFilter().filter(s);
                }else{
                    Toast.makeText(getContext(), "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                homeAllTabAdapter.getFilter().filter(s);
                return false;
            }
        });

    }
}