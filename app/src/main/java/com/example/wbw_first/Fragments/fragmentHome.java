package com.example.wbw_first.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.Interfaces.IDrawerLayout;
import com.example.wbw_first.MainActivity;
import com.example.wbw_first.R;
import com.example.wbw_first.TabsLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class fragmentHome extends Fragment {

    private View view;
    private Context context = null;
    private Activity activity;
    private Button btOpenLogin, btOpen1, btOpen2;
    private CardView cvOpenUser, cvOpenArea, cvOpenProgress, cvOpenHistory;
    private IDrawerLayout iDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicialize UI
        initUI();

        // Obtener el contexto
        context = getContext();

        // Listerner onclicks
        listenerOnClicks();

        return view;
    }

    // Inicializar vinculación con ui
    private void initUI(){
        cvOpenUser = view.findViewById(R.id.cvHomeOpenUser);
        cvOpenArea = view.findViewById(R.id.cvHomeOpenArea);
        cvOpenProgress = view.findViewById(R.id.cvHomeOpenProgress);
        cvOpenHistory = view.findViewById(R.id.cvHomeOpenHistory);
    }

    // Listener click
    private void listenerOnClicks(){
        // Navegación
        cvOpenUser.setOnClickListener(view1 -> {
            iDrawerLayout.ClickUser(view);
        });

        cvOpenArea.setOnClickListener(view1 -> {
            iDrawerLayout.ClickArea(view);
        });

        cvOpenProgress.setOnClickListener(view1 -> {
            iDrawerLayout.ClickProgress(view);
        });

        cvOpenHistory.setOnClickListener(view1 -> {
            iDrawerLayout.ClickHistory(view);
        });
    }

    // Redireccionar a otro activity
    public void redirectActivity(Activity activity, Class aClass) {
        // Initialize intent
        Intent intent = new Intent(activity, aClass);

        // Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start activity
        activity.startActivity(intent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof  Activity){
            activity = (Activity) context;
            iDrawerLayout = (IDrawerLayout) activity;
        }
    }
}