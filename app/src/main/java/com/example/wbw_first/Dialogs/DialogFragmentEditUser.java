package com.example.wbw_first.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;


public class DialogFragmentEditUser extends DialogFragment {

    Activity activity;
    View view;
    ImageButton ibClose;
    LinearLayout lyNavbarTop;
    Button btUpdate;
    EditText etName, etLastname;
    DialogFragmentEditUser.OnItemClickListener listener;
    EUser eUser;

    public interface OnItemClickListener {
        void onItemClickUpdate(EUser eUser);
    }

    public DialogFragmentEditUser() {
    }

    public DialogFragmentEditUser(EUser eUser, DialogFragmentEditUser.OnItemClickListener listener) {
        this.listener = listener;
        this.eUser = eUser;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialog();
    }

    private Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_dialog_edit_user, null);
        builder.setView(view);

        // inicializar refrencias con los widgets
        initUI();

        // Asignar los datos obtenidos por el constructor
        etName.setText(eUser.getNameuser());
        etLastname.setText(eUser.getLastname());

        listenerOnClicks();
        
        return builder.create();
    }

    private void initUI(){
        lyNavbarTop = view.findViewById(R.id.lyNavbarTopUser);
        ibClose = view.findViewById(R.id.ibCloseDialogUser);
        btUpdate = view.findViewById(R.id.btUpdateUser);
        etName = view.findViewById(R.id.etNameUserEdit);
        etLastname = view.findViewById(R.id.etLastnameEdit);
    }

    private void listenerOnClicks() {
        lyNavbarTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Top", Toast.LENGTH_SHORT).show();
            }
        });
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Evento lanzado desde afuera
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Actualizar datos
                    eUser.setNameuser(etName.getText().toString().trim());
                    eUser.setLastname(etLastname.getText().toString().trim());

                    listener.onItemClickUpdate(eUser);
                } catch (Exception ex){
                    throw new RuntimeException(ex.toString());
                } finally {
                    dismiss();
                }

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  Activity){
            this.activity = (Activity) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

}