package com.example.wbw_first.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.R;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class fragmentRegisterArea extends Fragment {

    private View view;
    private ModelArea modelArea;
    private Context context = null;
    private EditText etNameArea, etPriceArea;
    private Button btRegisterArea, btResetFormArea;
    private AutoCompleteTextView acSearchTypeArea;
    private ArrayAdapter<String> adapter;
    private String[] itemsSearch = {
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
        view = inflater.inflate(R.layout.fragment_register_area, container, false);

        // Inicializar refetrencias con el widgets
        initUI();
        context = getContext();
        modelArea = new ModelArea(context);

        // Autocopletado de tipo area
        loadAutoComplete();

        // listener click
        onClickListener();

        return view;
    }

    private void onClickListener() {
        btRegisterArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameArea = etNameArea.getText().toString().trim();
                String sPrice = etPriceArea.getText().toString().trim();

                if(nameArea.isEmpty() || sPrice.isEmpty()){
                    Toast.makeText(context, "Completar los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    confirmRegister(nameArea, Double.parseDouble(sPrice));
                }
            }
        });

        btResetFormArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFormArea();
            }
        });
    }

    private void initUI(){
        // Asignar variables
        acSearchTypeArea = view.findViewById(R.id.acTextSearchArea);
        etNameArea = view.findViewById(R.id.etNameareaAdd);
        etPriceArea = view.findViewById(R.id.etPriceAreaAdd);

        btRegisterArea = view.findViewById(R.id.btRegisterArea);
        btResetFormArea = view.findViewById(R.id.btResetFormArea);
    }

    private void loadAutoComplete(){
        // Adapter
        adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemsSearch);

        // Obtener la sugerencia despues del numero de palabra
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapter);
        acSearchTypeArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etNameArea.setText(adapter.getItem(i));
            }
        });
    }

    private void confirmRegister(String nameArea, double price){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Registro de area");
        builder.setMessage("¿Está seguro de agregar el area?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EArea eArea = new EArea();

                eArea.setNamearea(nameArea);
                eArea.setPrice(price);

                long idarea = modelArea.registerArea(eArea);

                if(idarea > 0){
                    resetFormArea(); // Limpiar controles
                    Toast.makeText(context, "Registrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al registrar, No puede duplicar el Area ", Toast.LENGTH_SHORT).show();
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

    private void resetFormArea(){
        acSearchTypeArea.clearListSelection();
        acSearchTypeArea.setText(null);
        etNameArea.setText(null);
        etPriceArea.setText(null);
    }
}