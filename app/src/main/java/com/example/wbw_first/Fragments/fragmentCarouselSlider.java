package com.example.wbw_first.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wbw_first.R;


public class fragmentCarouselSlider extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_carousel_slider, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}