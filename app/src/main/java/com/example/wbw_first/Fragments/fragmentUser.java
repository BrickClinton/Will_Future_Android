package com.example.wbw_first.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wbw_first.Adapters.VPAdapter;
import com.example.wbw_first.Adapters.VPUser;
import com.example.wbw_first.Controllers.PagerController;
import com.example.wbw_first.R;
import com.example.wbw_first.TabsLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class fragmentUser extends Fragment{

    // Declaración de variables
    private View view;
    private Button btOpenRegister, btOpenListUser, btOpenDesign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        // Inicializar referencias
        initUI();

        // Eventos
        onClickListener();

        return view;
    }

    /**
     * Inicializar interfaz referenciado con las variables
     */
    private void initUI(){
        btOpenRegister = view.findViewById(R.id.btOpenFragmentRegisterUser);
        btOpenListUser = view.findViewById(R.id.btOpenFragmentListUser);
        btOpenDesign = view.findViewById(R.id.btOpenFragmentDesignUser);
    }

    /**
     * Evento click de los botones
     */
    private void onClickListener(){
        btOpenRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentRegisterUser());
            }
        });
        btOpenListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentListUser());
            }
        });
        btOpenDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentCarouselSlider());
            }
        });
    }

    /**
     * Este método remplaza el contenido del fragment "fragmentContainer"
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContainerUser, fragment);
        fragmentTransaction.commit();
    }
}