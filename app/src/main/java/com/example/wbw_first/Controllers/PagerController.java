package com.example.wbw_first.Controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wbw_first.Fragments.fragmentArea;
import com.example.wbw_first.Fragments.fragmentHistory;
import com.example.wbw_first.Fragments.fragmentHome;

public class PagerController extends FragmentPagerAdapter  {

    int numOfTabs = 0;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new fragmentHome();
            case 1:
                return new fragmentHistory();
            case 2:
                return new fragmentArea();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
