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
import android.widget.Toast;

import com.example.wbw_first.Adapters.ListAdapterCard;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Dialogs.DialogFragmentDemo;
import com.example.wbw_first.Dialogs.DialogFragmentEditUser;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;

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
                DialogFragmentEditUser dialogFragmentEditUser = new DialogFragmentEditUser(eUser, new DialogFragmentEditUser.OnItemClickListener() {
                    @Override
                    public void onItemClickUpdate(EUser eUser) {
                        if(eUser.getNameuser().equals("") && eUser.getLastname().equals("")){
                            Toast.makeText(context, "DATOS INCOMPLETOS", Toast.LENGTH_SHORT).show();
                        } else {
                            modelUser.updateUser(eUser);
                            loadData();
                        }
                    }
                });
                dialogFragmentEditUser.show(getParentFragmentManager(), "DialogoUser");

            }

            @Override
            public void onItemClickDelete(EUser eUser) {
                // Instanciar dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Configurar
                builder.setTitle("Eliminar usuario");
                builder.setMessage("¿Está seguro de eliminar ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        modelUser.deleteUser(eUser.getIduser());
                        loadData();
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