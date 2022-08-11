package com.example.wbw_first.Dialogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.DataBase.ModelActivity;
import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EActivity;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class DialogRegisterActivity extends AppCompatActivity {

    private Context context = this;
    private ModelUser modelUser;
    private ModelArea modelArea;
    private ModelActivity modelActivity;
    private EActivity eActivity;
    private ArrayList<EArea> listArea;

    private Toolbar tbDialog;
    private AutoCompleteTextView acSearchTypeArea;
    private Button btRegisterActivity, btResetActivity;
    private EditText etNumberBox, etNameAndLastname;
    private int iduser = -1;
    private int idarea = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_register);

        // data base
        modelUser = new ModelUser(context);
        modelArea = new ModelArea(context);
        modelActivity = new ModelActivity(context);

        // initialize UI
        initUI();

        // Initialize data
        loadAutoCompleteTypeArea();
        loadData();

        // Onclick listener
        onClickListener();
    }

    private void initUI() {
        acSearchTypeArea = findViewById(R.id.acSearchDialogActivity);
        etNameAndLastname = findViewById(R.id.etNameUserDialogActivity);
        etNumberBox = findViewById(R.id.etNumberBoxDialogActivity);

        btRegisterActivity = findViewById(R.id.btRegisterDialogActivity);
        btResetActivity = findViewById(R.id.btResetFormDialogActivity);

        // Tolbar
        tbDialog = findViewById(R.id.tbReturnDialogActivity);
        final Drawable iconReturn = getResources().getDrawable(R.drawable.ic_return);
        iconReturn.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        tbDialog.setNavigationIcon(iconReturn);

        // Usando lamda para cerrar la actividad
        tbDialog.setNavigationOnClickListener(view -> {
            //overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
            this.finish();
        });
    }

    private void loadAutoCompleteTypeArea(){
        // Obteniendo la lista de areas registradas
        listArea = modelArea.getRows("ASC");

        ArrayAdapter<EArea> adapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listArea);
        acSearchTypeArea.setThreshold(1);
        acSearchTypeArea.setAdapter(adapter);

        acSearchTypeArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idarea = listArea.get(i).getIdarea(); // Obteniendo el idarea
            }
        });
    }

    private void loadData() {
        iduser = getIntent().getIntExtra("iduser", -1);
        etNameAndLastname.setText(getIntent().getStringExtra("username"));
    }

    private void onClickListener() {
        btRegisterActivity.setOnClickListener(view -> {
            String sNumberBox = etNumberBox.getText().toString().trim();

            if(sNumberBox.isEmpty() || iduser == -1 || idarea == -1){
                Toast.makeText(context, "Completar los datos" , Toast.LENGTH_SHORT).show();
            } else {
                eActivity = new EActivity();
                eActivity.setIduser(iduser);
                eActivity.setIdarea(idarea);
                eActivity.setNumberbox(Integer.parseInt(sNumberBox));
                registerActivity(eActivity);
            }
        });

        btResetActivity.setOnClickListener(view -> {
            resetFormActivity();
        });
    }

    private void registerActivity(EActivity eActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ACTIVIDAD");
        builder.setMessage("¿Está seguro de guardar el avance?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            long idactivity = modelActivity.registerActivity(eActivity);

            if(idactivity > 0){
                idarea = 0;
                resetFormActivity();
                Toast.makeText(context, "Registrado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
        });

        builder.show();
    }

    private void resetFormActivity(){
        //iduser = -1;
        idarea = -1;
        acSearchTypeArea.clearListSelection();
        acSearchTypeArea.setText(null);
        etNumberBox.setText(null);
        acSearchTypeArea.requestFocus();
    }

}