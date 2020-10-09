package com.likeview.csm.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.likeview.csm.ApiResponse.ApiResponse;
import com.likeview.csm.ApiResponse.Model.ListClientModel;
import com.likeview.csm.Connectivity.CheckNetwork;
import com.likeview.csm.R;
import com.likeview.csm.adapter.NavSaveAdapter;
import com.likeview.csm.api.Api;
import com.likeview.csm.api.RetrofitClient;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.aswdc.autocadetutorial.adapter.VideoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavSaveFragment extends Fragment {
    TextView toolbartext;
    TextView textUserName ,textPhoneNumber,textEmail,textCity,textState,textCountry,textPincode;
    ImageView textEdit;
    CircleImageView imgUserProfilePhoto;
    Button btnLogout;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    Context context;
    RecyclerView rcvallSubFileList;
    SwipeRefreshLayout swipeHome;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView navUserName;
    ImageView backButton;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    private ShimmerFrameLayout mShimmerViewContainer;
//    SharedPrefManager sfm = SharedPrefManager.getInstance(getActivity());
//    ProfileDetail pd = sfm.getUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_nav_save, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        swipeHome = view.findViewById( R.id.swipeHome );
        toolbar = view.findViewById( R.id.toolbar );
        rcvallSubFileList = view.findViewById( R.id.rcvallSubFileList );
        navUserName  = view.findViewById( R.id.navUserName );
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_save);


        manager = new LinearLayoutManager( getActivity() );
        rcvallSubFileList.setLayoutManager( manager );
        toolbartext = view.findViewById( R.id.toolbartext );
        toolbartext.setText( "Save" );
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

        Api api = RetrofitClient.getApi().create(Api.class);
        Call<ApiResponse> call = api.getsaveclientlists();
        call.enqueue( new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getResCode() == 1) {
//                    progress.dismiss();
                    ArrayList<ListClientModel> subfilesWithUserDetailHistories = (ArrayList<ListClientModel>) response.body().getResData().getListClient();
                    rcvallSubFileList.setAdapter(new NavSaveAdapter(getActivity(), subfilesWithUserDetailHistories));
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
                else
                {
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
//                    progress.dismiss();
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }
//                progress.dismiss();

            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Z",""+t.getLocalizedMessage());
                Toast.makeText( getActivity(), t.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
//                progress.dismiss();
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);

            }
        } );

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
