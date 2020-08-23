package com.likeview.csm.Fragment;

import com.google.android.material.tabs.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.likeview.csm.R;
import com.likeview.csm.adapter.HomeAllTabAdapter;
import com.likeview.csm.adapter.ViewPageAdapter;
import com.likeview.csm.storage.SharedPrefManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

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
    ViewPager viewPager;
    TabLayout tabLayout;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


//        SharedPrefManager sfm = SharedPrefManager.getInstance(getActivity());
//        ProfileDetail pd = sfm.getUser();

//        navUserName.setText( pd.getCity() );

        // Toolbar

        TextView toolbartext;
        toolbar=  view.findViewById(R.id.toolbar);
        toolbartext=  view.findViewById(R.id.toolbartext);
        toolbartext.setText( "CSM" );
        tabLayout = view.findViewById( R.id.tablayout_id );
        viewPager = view.findViewById( R.id.viewpager_id );
//        FInd ID

        drawerLayout= view.findViewById(R.id.drawer_layout);
        navigationView= view.findViewById(R.id.nav_view);

//        set nevigation drawer

        actionBarDrawerToggle = new ActionBarDrawerToggle( getActivity(),drawerLayout,toolbar, R.string.open,R.string.close );
        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.setDrawerIndicatorEnabled( true );
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );


        initializeReference();
    }

    void initializeReference() {
        ViewPageAdapter adapter = new ViewPageAdapter( getChildFragmentManager() );
        adapter.AddFragment(new AllHomeTabFragment(), "All" );
        adapter.AddFragment( new HomeTabFragment(1), "High Price" );
        adapter.AddFragment(new HomeTabFragment(2), "Have Dealers" );
        adapter.AddFragment( new HomeTabFragment(3), "Design Issue" );
        adapter.AddFragment(new HomeTabFragment(4), "Quality Issue" );
        adapter.AddFragment( new HomeTabFragment(5), "Avaliblity" );
        adapter.AddFragment( new HomeTabFragment(6), "Panding" );

        //adapter setup
        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );
    }

//      create method






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_add_rupees:
                Toast.makeText( getActivity(), "AddArchRuppes", Toast.LENGTH_LONG ).show();
//                Intent intent = new Intent( getActivity(), AddRuppesActivity.class );
//                startActivity( intent );
                /*FragmentManager fragmentManager = getActivity().getFragmentManager(); // For AppCompat use getSupportFragmentManager
                fragmentManager.beginTransaction().replace( R.id.fragment_container ,new AddArchRupeeFragment()).commit();*/
                break;
            case R.id.nav_wallet_to_bank:
                Toast.makeText( getActivity(), "Wallet to bank", Toast.LENGTH_LONG ).show();
//                Intent intent1 = new Intent( getActivity(), WalletToBankAcountActivity.class );
//                startActivity( intent1 );
                break;

            case R.id.nav_passbook:
                Toast.makeText( getActivity(), "Passbook", Toast.LENGTH_LONG ).show();
//                Intent intent2 = new Intent( getActivity(), PassbookActivity.class );
//                startActivity( intent2 );
                break;
            case R.id.nav_add_event:
                Toast.makeText( getActivity(), "Add Event", Toast.LENGTH_LONG ).show();
//                Intent intent3 = new Intent( getActivity(), AddEventsActivity.class );
//                startActivity( intent3 );
                break;
            case R.id.nav_logout:
                Toast.makeText( getActivity(), "Log Out", Toast.LENGTH_LONG ).show();
                SharedPrefManager sfm = SharedPrefManager.getInstance(context);
                sfm.clear();

//                Intent i = new Intent( getActivity(), LoginActivity.class);
//                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                break;
        }

        drawerLayout.closeDrawer( GravityCompat.START );
        return true;

    }

}
