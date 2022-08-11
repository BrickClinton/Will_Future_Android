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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.Adapters.AdapterListArea;
import com.example.wbw_first.Adapters.ListAdapterCard;
import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.Interfaces.IOnClickListenerGeneric;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class fragmentListArea extends Fragment implements SearchView.OnQueryTextListener{

    private View view;
    private Context context = null;
    private ModelArea modelArea;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<EArea> listArea;
    private LinearLayoutManager linearLayoutManager;
    private AdapterListArea adapter;
    private SearchView searchArea;

    // variables usados en el bottomSheet
    private Button btCloseBottomSheet, btUpdateArea;
    private EditText etPriceAreaEdit;
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
        view = inflater.inflate(R.layout.fragment_list_area, container, false);


        // inicialize ui
        initUI();

        // Contexto
        context = getContext();

        // Instancia de datos
        modelArea = new ModelArea(context);

        loadData();

        searchArea.setOnQueryTextListener(this);

        // Actualizar datos del recyclerView
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private void initUI() {
        recyclerView = view.findViewById(R.id.rvListArea);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutArea);
        searchArea = view.findViewById(R.id.searchArea);
    }

    private void loadData() {
        // Obtener los datos
        listArea = modelArea.getRows("DESC");

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterListArea(context, listArea, new IOnClickListenerGeneric<EArea>() {
            @Override
            public void onItemClickEdit(EArea eArea) {
                openBottomSheet(eArea);
            }

            @Override
            public void onItemClickDelete(EArea eArea) {
                validateDelete(eArea.getIdarea());
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void openBottomSheet(EArea eArea){
        // Crear modal bottom
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_area);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // Inicializar UI
        acSearchTypeArea = bottomSheetDialog.findViewById(R.id.acTypeAreaBottomSheet);
        etPriceAreaEdit = bottomSheetDialog.findViewById(R.id.etPriceAreaEdit);
        btUpdateArea = bottomSheetDialog.findViewById(R.id.btUpdateArea);
        btCloseBottomSheet = bottomSheetDialog.findViewById(R.id.btCloseBSArea);

        // Datos traidos
        acSearchTypeArea.setText(eArea.getNamearea());
        etPriceAreaEdit.setText(String.valueOf(eArea.getPrice()));

        // Listener autocomplete
        loadAutoCompleteTypeArea();

        // Listener onclick
        onClickListenerBottomSheet(bottomSheetDialog, eArea);

        // Mostrar bottomSheetdialog
        bottomSheetDialog.show();
    }

    private void loadAutoCompleteTypeArea(){
        // Adapter
        adapterAutocomplete = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemsTypeArea);

        // Obtener la sugerencia despues del numero de palabra
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapterAutocomplete);
    }

    private void onClickListenerBottomSheet(BottomSheetDialog bottomSheetDialog, EArea eArea){
        btUpdateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameArea = acSearchTypeArea.getText().toString().trim();
                String sPrice = etPriceAreaEdit.getText().toString().trim();

                // Actualizar datos
                eArea.setNamearea(nameArea);
                eArea.setPrice(Double.parseDouble(sPrice));

                if(nameArea.isEmpty() || sPrice.isEmpty()){
                    Toast.makeText(context, "Completar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    validateUpdate(eArea);
                }

                bottomSheetDialog.dismiss();
            }
        });
        btCloseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void validateUpdate(EArea eArea){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ACTUALIZAR AREA");
        builder.setMessage("¿Está seguro de actualizar?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long idarea = modelArea.updateArea(eArea);

                if(idarea > 0){
                    loadData();
                    Toast.makeText(context, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // Mostrar
        builder.show();
    }

    private void validateDelete(int idarea){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ELIMINAR AREA");
        builder.setMessage("¿Está seguro de eliminar el registro?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long value = modelArea.deleteArea(idarea);

                if(value > 0){
                    loadData();
                    Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        // Mostrar
        builder.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filteredUser(newText);
        return false;
    }
}