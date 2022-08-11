package com.example.wbw_first.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.Adapters.AdapterListActivity;
import com.example.wbw_first.Adapters.AdapterListUserHorizontal;
import com.example.wbw_first.DataBase.ModelActivity;
import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Dialogs.DialogListActivity;
import com.example.wbw_first.Dialogs.DialogRegisterActivity;
import com.example.wbw_first.Entities.EActivity;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class fragmentWorkProgress extends Fragment {

    private View view;
    private Context context = null;
    private ModelUser modelUser;
    private RecyclerView recyclerView;
    private ArrayList<EUser> listUsers;
    private AdapterListUserHorizontal adapter;
    private Button btOpenActivityRegister, btOpenActivityList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_work_progress, container, false);

        // data base
        context = getContext();
        modelUser = new ModelUser(context);

        // Init
        initUI();

        // Cargar datod
        loadData();

        return view;
    }

    private void initUI() {
        recyclerView = view.findViewById(R.id.rvListUserHorizontal);
    }

    // Iniciar carga de datos (cardview)
    private void loadData(){
        // Obtener los datos
        listUsers = modelUser.getRows();

        // Horizontal
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterListUserHorizontal(context, listUsers, new AdapterListUserHorizontal.OnItemClickListener() {
            @Override
            public void onItemClickCard(EUser eUser) {
                openBottomSheetOption(eUser);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void openBottomSheetOption(EUser eUser){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_option_activity);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // Inicializar referencias con los widgets
        btOpenActivityRegister = bottomSheetDialog.findViewById(R.id.btBsOptionActivityAdd);
        btOpenActivityList = bottomSheetDialog.findViewById(R.id.btBsOptionActivityList);

        // onclick listener
        bottomSheetOnClick(bottomSheetDialog, eUser);

        bottomSheetDialog.show();
    }

    private void bottomSheetOnClick(BottomSheetDialog bsDialog, EUser eUser){
        btOpenActivityRegister.setOnClickListener(view1 -> {
            openActivity(eUser, DialogRegisterActivity.class);
            bsDialog.dismiss();
        });

        btOpenActivityList.setOnClickListener(view1 -> {
            openActivity(eUser, DialogListActivity.class);
            bsDialog.dismiss();
        });
    }

    private void openActivity(EUser eUser, Class aClass){
        Intent intent = new Intent(context, aClass);
        intent.putExtra("iduser", eUser.getIduser());
        intent.putExtra("username", eUser.getNameuser() + " " + eUser.getLastname());
        startActivity(intent);
    }
}