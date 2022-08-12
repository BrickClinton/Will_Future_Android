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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.Adapters.ListAdapterCard;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class fragmentListUser extends Fragment implements SearchView.OnQueryTextListener{

    // Declaración de variables (reservar espacio en memoria)
    private View view;
    private Context context = null;
    private ModelUser modelUser;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<EUser> listUsers;
    private LinearLayoutManager linearLayoutManager;
    private ListAdapterCard adapter;
    private SearchView searchUser;

    private Button btClose, btUpdate;
    private EditText etName, etLastname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_user, container, false);

        // inicialize ui
        initUI();

        // Contexto
        context = getContext();

        // Instancia de datos
        modelUser = new ModelUser(context);

        loadData();

        searchUser.setOnQueryTextListener(this);

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

    // Inicializar refrencias con los widgets
    private void initUI(){
        recyclerView = view.findViewById(R.id.rvListUser);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        searchUser = view.findViewById(R.id.searchUser);
    }

    // Iniciar carga de datos (cardview)
    private void loadData(){
        // Obtener los datos
        listUsers = modelUser.getRows();

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ListAdapterCard(context, listUsers, new ListAdapterCard.OnItemClickListener() {
            @Override
            public void onItemClickEdit(EUser eUser) {
                openBottomSheet(eUser);
                //loadData();
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClickDelete(EUser eUser) {
                // Instanciar dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Configurar
                builder.setTitle("ELIMINAR USUARIO");
                builder.setMessage("¿Está seguro de eliminar ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long iduser = modelUser.deleteUser(eUser.getIduser());

                        if(iduser > 0){
                            loadData();
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
        });

        recyclerView.setAdapter(adapter);
    }

    private void openBottomSheet(EUser eUser){
        // Crear modal bottom
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_update_user);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // Inicializar UI
        etName = bottomSheetDialog.findViewById(R.id.etNameuserBsUser);
        etLastname = bottomSheetDialog.findViewById(R.id.etLastnameBsUser);

        btUpdate = bottomSheetDialog.findViewById(R.id.btUpdateBsUser);
        btClose = bottomSheetDialog.findViewById(R.id.btCloseBsUser);

        // Datos traidos
        etName.setText(eUser.getNameuser());
        etLastname.setText(eUser.getLastname());

        // Listener onclick
        onClickListenerBottomSheet(bottomSheetDialog, eUser);

        // Mostrar bottomSheetdialog
        bottomSheetDialog.show();
    }

    private void onClickListenerBottomSheet(BottomSheetDialog bottomSheetDialog, EUser eUser){
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String lastname = etLastname.getText().toString().trim();

                if(name.isEmpty() || lastname.isEmpty()){
                    Toast.makeText(context, "Complete los datos", Toast.LENGTH_SHORT).show();
                } else {
                    eUser.setNameuser(name);
                    eUser.setLastname(lastname);

                    questionUpdate(bottomSheetDialog, eUser);
                }
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void questionUpdate(BottomSheetDialog bottomSheetDialog ,EUser eUser){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ACTUALIZAR USUARIO");
        builder.setMessage("¿Está seguro de actualizar el registro?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long iduser = modelUser.updateUser(eUser);

                if(iduser > 0){
                    bottomSheetDialog.dismiss();
                    loadData();
                    Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

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