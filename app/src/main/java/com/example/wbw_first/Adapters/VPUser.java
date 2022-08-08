package com.example.wbw_first.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wbw_first.Fragments.fragmentArea;
import com.example.wbw_first.Fragments.fragmentCarouselSlider;
import com.example.wbw_first.Fragments.fragmentHome;
import com.example.wbw_first.Fragments.fragmentListUser;
import com.example.wbw_first.Fragments.fragmentRegisterUser;

import java.util.ArrayList;

public class VPUser extends FragmentPagerAdapter {

    private int numOgTabs = 0;

    public VPUser(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOgTabs = behavior;

    }


    @NonNull
    @Override
    public  Fragment getItem(int position) {
        switch (position){
            case 0: return new fragmentRegisterUser();
            case 1: return new fragmentListUser();
            case 2: return new fragmentCarouselSlider();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOgTabs;
    }
}
