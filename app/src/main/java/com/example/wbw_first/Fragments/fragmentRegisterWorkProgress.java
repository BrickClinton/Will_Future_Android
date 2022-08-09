package com.example.wbw_first.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.Adapters.AdapterListUserHorizontal;
import com.example.wbw_first.Adapters.ListAdapterCard;
import com.example.wbw_first.BottomSheet.dialogBottomRegisterActivity;
import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class fragmentRegisterWorkProgress extends Fragment {

    private View view;
    private Context context = null;
    private ModelUser modelUser;
    private ModelArea modelArea;
    private RecyclerView recyclerView;
    private ArrayList<EUser> listUsers;
    private ArrayList<EArea> listArea;
    private AdapterListUserHorizontal adapter;

    private AutoCompleteTextView acSearchTypeArea;
    private ArrayAdapter<String> adapterAutocomplete;
    private String[] itemsTypeArea = {
            "Apilador",
            "Selección",
            "Embalaje",
            "Pesador valanza",
            "Pesador clanche 1/8",
            "Pesador clanche 1/4",
            "Pesador clanche 1/2",
            "Pesador clanche 1",
            "Pesador clanche 1.5",
            "Pesador clanche 2"
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_work_progress, container, false);

        // data base
        context = getContext();
        modelUser = new ModelUser(context);
        modelArea = new ModelArea(context);

        // Init
        initUI();

        // Cargar datod
        loadData();

        // click listener
        onClickListener();

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
                openBottomSheet(eUser);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void openBottomSheet(EUser eUser){
        // Crear modal bottom
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.activity_dialog_bottom_register);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // Inicializar UI
        EditText etNameAndLastname = bottomSheetDialog.findViewById(R.id.etNameUserBSActivity);
        acSearchTypeArea = bottomSheetDialog.findViewById(R.id.acSearchBSActivity);

        // Datos traidos
        etNameAndLastname.setText(eUser.getNameuser() + " " + eUser.getLastname());

        loadAutoCompleteTypeArea();

        // Mostrar bottomSheetdialog
        bottomSheetDialog.show();
    }

    private void loadAutoCompleteTypeArea(){
        // Adapter
       /* adapterAutocomplete = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemsTypeArea);

        // Obtener la sugerencia despues del numero de palabra
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapterAutocomplete);
        acSearchTypeArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });*/

        // Creando el arrayList
        listArea = modelArea.getRows();

        ArrayAdapter<EArea> adapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listArea);
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapter);

        acSearchTypeArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickListener(){

    }
}