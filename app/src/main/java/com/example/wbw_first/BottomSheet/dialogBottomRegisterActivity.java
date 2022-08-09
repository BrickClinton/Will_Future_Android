package com.example.wbw_first.BottomSheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.wbw_first.R;

public class dialogBottomRegisterActivity extends AppCompatActivity {

    private Context context = this;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_bottom_register);

        //initUI();
        //loadAutoCompleteTypeArea();
    }

    private void initUI(){
        acSearchTypeArea = findViewById(R.id.acSearchBSActivity);
    }

    private void loadAutoCompleteTypeArea(){
        // Adapter
        adapterAutocomplete = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemsTypeArea);

        // Obtener la sugerencia despues del numero de palabra
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapterAutocomplete);
        acSearchTypeArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }

}