package com.likeview.csm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.likeview.csm.R;
import com.likeview.csm.adapter.ViewPageAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View root = inflater.inflate( R.layout.fragment_home, container, false );
        final TextView textView = root.findViewById( R.id.text_home );
        homeViewModel.getText().observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText( s );
            }
        } );
        return root;
    }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated( view, savedInstanceState );
//
//        // Toolbar
//
//        TextView toolbartext;
//        toolbar=  view.findViewById(R.id.toolbar);
//        toolbartext=  view.findViewById(R.id.toolbartext);
//        toolbartext.setText( "Winners" );
//        tabLayout = view.findViewById( R.id.tablayout_id );
//        viewPager = view.findViewById( R.id.viewpager_id );
//        initializeReference();
//    }
//    void initializeReference() {
//        ViewPageAdapter adapter = new ViewPageAdapter( getChildFragmentManager() );
//        adapter.AddFragment(new AllWinnerListFragment(), "All" );
//        adapter.AddFragment( new UserWinnerListFragment(), "You" );
//        //adapter setup
//        viewPager.setAdapter( adapter );
//        tabLayout.setupWithViewPager( viewPager );
//    }
}