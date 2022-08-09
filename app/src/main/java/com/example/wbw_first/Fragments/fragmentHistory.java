package com.example.wbw_first.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wbw_first.R;

public class fragmentHistory extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        // Fragmento mostrado por defecto
        replaceFragment(new fragmentCarouselSlider());

        return view;
    }

    /**
     * Este método remplaza el contenido del fragment "fragmentContainer"
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContainerHistory, fragment);
        fragmentTransaction.commit();
    }
}