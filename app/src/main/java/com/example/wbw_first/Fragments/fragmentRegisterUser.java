package com.example.wbw_first.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.wbw_first.DataBase.DBAccess;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.MainActivity;
import com.example.wbw_first.R;
import com.example.wbw_first.TabsLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class fragmentRegisterUser extends Fragment{

    // Declaración de variables
    private View view;
    private EditText etNameuser, etLastname;
    private Button btRegister, btReset;
    private Context context = null;
    private ModelUser modelUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_user, container, false);

        // Init UI
        initUI();

        context = getContext();
        modelUser = new ModelUser(context);

        // Listerner click
        listenerOnClicks();

        return view;
    }

    // Inicializar vinculación con ui
    private void initUI(){
        etNameuser = view.findViewById(R.id.etNameUserAdd);
        etLastname = view.findViewById(R.id.etLastnameAdd);

        btRegister = view.findViewById(R.id.btRegisterUser);
        btReset = view.findViewById(R.id.btResetFormUserAdd);
    }

    // Listener click
    private void listenerOnClicks(){
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFormUser();
            }
        });
    }

    // Validar datos
    private boolean dataRegisterIsEmpty(){
        return etNameuser.getText().toString().isEmpty() || etLastname.getText().toString().isEmpty();
    }

    // Resetear controles
    private void clearFormUser(){
        etNameuser.setText(null);
        etLastname.setText(null);
        etNameuser.setSelected(true);
    }

    // Registrar
    private void registerUser(){

        if(dataRegisterIsEmpty()){
            Toast.makeText(context, "Datos incompletos", Toast.LENGTH_SHORT).show();
        } else {
            // Objeto Alert dialogo
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);

            dialog.setTitle("User")
                    .setMessage("¿Está seguro de crear su cuenta?")
                    .setCancelable(false)
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EUser eUser = new EUser();
                            eUser.setNameuser(etNameuser.getText().toString().trim());
                            eUser.setLastname(etLastname.getText().toString().trim());
                            long iduser = modelUser.registerUser(eUser);

                            if(iduser > 0){
                                // Limpiar controles de registro
                                clearFormUser();
                                Toast.makeText(context, "Guardado con éxito: " + iduser, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error al registrar: " + iduser, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });


            // Mostrar dialogo
            dialog.show();
        }
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

}