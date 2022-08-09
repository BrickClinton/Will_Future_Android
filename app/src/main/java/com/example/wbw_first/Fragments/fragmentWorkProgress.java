package com.example.wbw_first.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wbw_first.R;

public class fragmentWorkProgress extends Fragment {

    private View view;
    private Button btOpenRegister, btOpenList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_work_progress, container, false);

        // Initialize UI
        initUI();

        // Listener onclick
        onClickListener();

        // Fragmento mostrado por defecto
        replaceFragment(new fragmentListWorkProgress());

        return view;
    }

    /**
     * Inicializar interfaz referenciado con las variables
     */
    private void initUI(){
        btOpenRegister = view.findViewById(R.id.btOpenFragmentRegisterWorkProgress);
        btOpenList = view.findViewById(R.id.btOpenFragmentListWorkProgress);
    }

    /**
     * Evento click de los botones
     */
    private void onClickListener(){
        btOpenRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentRegisterWorkProgress());
            }
        });
        btOpenList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentListWorkProgress());
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

        fragmentTransaction.replace(R.id.flContainerWorkProgress, fragment);
        fragmentTransaction.commit();
    }
}