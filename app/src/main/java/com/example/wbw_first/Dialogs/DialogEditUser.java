package com.example.wbw_first.Dialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;

public class DialogEditUser extends AppCompatActivity {

    private ModelUser modelUser;
    private EUser eUser;
    private Toolbar tbDialog;
    private EditText etNameUser, etLastname;
    private Button btUpdate, btClose;
    private Context context = this;
    private int iduser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_edit_user);

        // Instance DB
        modelUser = new ModelUser(context);

        // initialize UI
        initUI();

        // Initialize data
        loadData();

        // Onclick listener
        onClickListener();
    }

    private void initUI(){

        // Editext
        etNameUser = findViewById(R.id.etNameuserDialogUser);
        etLastname = findViewById(R.id.etLastnameDialogUser);

        // Button
        btUpdate = findViewById(R.id.btUpdateDialogUser);
        btClose = findViewById(R.id.btCloseDialogUser);

        // Tobar
        tbDialog = findViewById(R.id.tbReturnDialogEdituser);
        final Drawable iconReturn = getResources().getDrawable(R.drawable.ic_return);
        iconReturn.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        tbDialog.setNavigationIcon(iconReturn);

        // Usando lamda
        tbDialog.setNavigationOnClickListener(view -> {
            //overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
            this.finish();
        });

    }

    private void loadData() {
        etNameUser.setText(getIntent().getStringExtra("username"));
        etLastname.setText(getIntent().getStringExtra("lastname"));
        iduser = getIntent().getIntExtra("iduser", 0);
    }

    private void onClickListener() {
        btUpdate.setOnClickListener(view -> {
            String username = etNameUser.getText().toString().trim();
            String lastname = etLastname.getText().toString().trim();

            if(username.isEmpty() || lastname.isEmpty()){
                Toast.makeText(context, "Complete los campos de texto", Toast.LENGTH_SHORT).show();
            } else {
                eUser = new EUser();
                eUser.setIduser(iduser);
                eUser.setNameuser(username);
                eUser.setLastname(lastname);

                questionUpdate(eUser);
            }
        });

        btClose.setOnClickListener(view -> {
            finish();
        });
    }

    private void questionUpdate(EUser eUser){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ACTUALIZAR CUENTA");
        builder.setMessage("¿Está seguro de actualizar el registro?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            long iduser = modelUser.updateUser(eUser);

            if(iduser > 0){
                finish();
                Toast.makeText(this, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });

        builder.show();
    }
}