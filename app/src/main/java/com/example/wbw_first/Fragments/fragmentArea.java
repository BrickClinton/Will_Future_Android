package com.example.wbw_first.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wbw_first.R;
import com.example.wbw_first.Utilities.Drawer;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class fragmentArea extends Fragment {

    private View view;
    private Button btOpenFragRegister, btOpenFragList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_area, container, false);

        // Initialize UI
        initUI();

        // Listener onclick
        onClickListener();

        return view;
    }

    private void initUI(){
        // Asignar variables
        btOpenFragRegister = view.findViewById(R.id.btOpenFragmentRegisterArea);
        btOpenFragList = view.findViewById(R.id.btOpenFragmentListArea);
    }


    /**
     * Evento click de los botones
     */
    private void onClickListener(){
        btOpenFragRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentRegisterArea());
            }
        });

        btOpenFragList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new fragmentListArea());
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

        fragmentTransaction.replace(R.id.flContainerArea, fragment);
        fragmentTransaction.commit();
    }
}