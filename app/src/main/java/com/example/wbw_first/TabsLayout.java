package com.example.wbw_first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wbw_first.Adapters.VPAdapter;
import com.example.wbw_first.Controllers.PagerController;
import com.example.wbw_first.Fragments.fragmentArea;
import com.example.wbw_first.Fragments.fragmentCarouselSlider;
import com.example.wbw_first.Fragments.fragmentHistory;
import com.example.wbw_first.Fragments.fragmentHome;
import com.example.wbw_first.Fragments.fragmentListUser;
import com.example.wbw_first.Fragments.fragmentRegisterUser;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TabsLayout extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tab1, tab2, tab3;
    PagerController pagerController;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> fragmentTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_layout);

        // Inicialize ui
        initUI();

        // Config tab layout
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        fragments = new ArrayList<>();
        fragments.add(new fragmentHome());
        fragments.add(new fragmentHistory());
        fragments.add(new fragmentArea());
        fragments.add(new fragmentCarouselSlider());
        fragments.add(new fragmentRegisterUser());
        fragments.add(new fragmentListUser());

        fragmentTitles = new ArrayList<>();
        fragmentTitles.add("Home");
        fragmentTitles.add("History");
        fragmentTitles.add("Area");
        fragmentTitles.add("Carousel");
        fragmentTitles.add("Register");
        fragmentTitles.add("List user");


        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), fragments, fragmentTitles);
        viewPager.setAdapter(vpAdapter);

    }

    private void initUI(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tab1 = findViewById(R.id.tbOne);
        tab2 = findViewById(R.id.tbTwo);
        tab3 = findViewById(R.id.tbThree);
    }

    private void addIconTab(String text, int icon){
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        tab.setIcon(icon);
        tabLayout.addTab(tab);
    }

    private void loadListener(){
        // /*
        pagerController = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerController);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerController.notifyDataSetChanged();

                if(tab.getPosition() == 0){
                    Toast.makeText(TabsLayout.this, "Test1", Toast.LENGTH_SHORT).show();
                }

                if(tab.getPosition() == 1){
                    Toast.makeText(TabsLayout.this, "Test2", Toast.LENGTH_SHORT).show();
                }

                if(tab.getPosition() == 2){
                    Toast.makeText(TabsLayout.this, "Test3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //  */
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}