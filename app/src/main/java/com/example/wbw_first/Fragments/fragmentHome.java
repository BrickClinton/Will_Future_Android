package com.example.wbw_first.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.MainActivity;
import com.example.wbw_first.R;
import com.example.wbw_first.TabsLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class fragmentHome extends Fragment {

    private View view;
    private Context context = null;
    private Button btOpenLogin, btOpen1, btOpen2, btQuery;

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
        btOpenLogin = view.findViewById(R.id.btShowModalBottom);
        btOpen1 = view.findViewById(R.id.btOpen1);
        btOpen2 = view.findViewById(R.id.btOpen2);
        btQuery = view.findViewById(R.id.btQuest);
    }

    // Listener click
    private void listenerOnClicks(){
        btOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear modal bottom
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                // Initialize
                EditText etUserAccount, etPasswordAccount;
                Button btSubmit;

                etUserAccount = bottomSheetDialog.findViewById(R.id.etUserAccount);
                etPasswordAccount = bottomSheetDialog.findViewById(R.id.etPasswordAccount);
                btSubmit = bottomSheetDialog.findViewById(R.id.btSubmitAccount);
                etUserAccount.setText("testtt");

                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Set validate
                        if(etUserAccount.getText().toString().equals("admin") && etPasswordAccount.getText().toString().equals("admin")){
                            // Cerrar modal
                            bottomSheetDialog.cancel();

                            // Crear dialogo
                            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                            dialog.setTitle("Login");
                            dialog.setMessage("Accediendo");
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            dialog.show();

                        } else {
                            Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Mostrar bottomSheetdialog
                bottomSheetDialog.show();
            }
        });

        btOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirectActivity(getActivity(), MainActivity.class);
            }
        });

        btOpen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(getActivity(), TabsLayout.class);
            }
        });

        btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Message test");
                builder.setMessage("Este es un dialogo de prueba");
                builder.setCancelable(false);
                builder.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
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
}