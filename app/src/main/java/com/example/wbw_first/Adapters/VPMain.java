package com.example.wbw_first.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wbw_first.Fragments.fragmentArea;
import com.example.wbw_first.Fragments.fragmentHistory;
import com.example.wbw_first.Fragments.fragmentHome;
import com.example.wbw_first.Fragments.fragmentUser;
import com.example.wbw_first.Fragments.fragmentWorkProgress;


public class VPMain extends FragmentPagerAdapter {

    private int numOgTabs = 0;

    public VPMain(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOgTabs = behavior;

    }

    @NonNull
    @Override
    public  Fragment getItem(int position) {
        switch (position){
            case 0: return new fragmentHome();
            case 1: return new fragmentUser();
            case 2: return new fragmentArea();
            case 3: return new fragmentWorkProgress();
            case 4: return new fragmentHistory();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOgTabs;
    }
}
