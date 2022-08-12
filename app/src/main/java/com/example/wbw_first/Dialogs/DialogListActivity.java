package com.example.wbw_first.Dialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbw_first.Adapters.AdapterListActivity;
import com.example.wbw_first.DataBase.ModelActivity;
import com.example.wbw_first.DataBase.ModelArea;
import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EActivity;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class DialogListActivity extends AppCompatActivity {

    private Context context = this;
    private ModelUser modelUser;
    private ModelArea modelArea;
    private ModelActivity modelActivity;
    private RecyclerView rvListActivity;
    private ArrayList<EArea> listArea;
    private ArrayList<EActivity> listActivity;
    private AdapterListActivity adapterListActivity;
    private SwipeRefreshLayout swipeRefreshLayoutActivity;
    private Toolbar tbDialog;
    private ImageView ivDateStart, ivDateEnd;
    private EditText etDateStart, etDateEnd;
    private TextInputLayout tilDateStart, tilDateEnd;
    private ImageButton ibFilterActivity;
    private TextView tvTotalMoney;

    private Button btCancel, btUpdate;
    private AutoCompleteTextView acSearchArea;
    private EditText etNumberBox;
    private EActivity eActivity;
    private int idarea = -1;
    private double totalMoney = 0;

    final Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.HOUR_OF_DAY);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);

        // data base
        modelUser = new ModelUser(context);
        modelArea = new ModelArea(context);
        modelActivity = new ModelActivity(context);

        // Init
        initUI();

        // Cargar datod
        loadData();

        onClickListener();

        swipeRefreshLayoutActivity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                adapterListActivity.notifyDataSetChanged();
                swipeRefreshLayoutActivity.setRefreshing(false);
            }
        });
    }

    private void initUI() {
        rvListActivity = findViewById(R.id.rvListActivity);
        swipeRefreshLayoutActivity = findViewById(R.id.swipeRefreshLayoutActivity);

        etDateStart = findViewById(R.id.etDateRangeStart);
        etDateEnd = findViewById(R.id.etDateRangeEnd);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);

        tilDateStart = findViewById(R.id.tilDateRangeStart);
        tilDateEnd = findViewById(R.id.tilDateRangeEnd);
        ibFilterActivity = findViewById(R.id.ibFilterActivity);

        tbDialog = findViewById(R.id.tbReturnActivityList);
        final Drawable iconReturn = getResources().getDrawable(R.drawable.ic_return);
        iconReturn.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        tbDialog.setNavigationIcon(iconReturn);
        tbDialog.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData(){
        int iduser = getIntent().getIntExtra("iduser", -1);

        listActivity = modelActivity.getRowsByIduser(iduser);

        String tMoney = String.valueOf(getTotalMoney(listActivity));
        tvTotalMoney.setText(tMoney);

        rvListActivity.setLayoutManager(new LinearLayoutManager(context));
        rvListActivity.setHasFixedSize(true);
        adapterListActivity = new AdapterListActivity(context, listActivity, new AdapterListActivity.OnItemClickListener() {
            @Override
            public void onItemClickEdit(EActivity eActivity) {
                openBottomSheet(eActivity);
            }

            @Override
            public void onItemClickDelete(EActivity eActivity) {
                questionDelete(eActivity.getIdactivity());
            }
        });

        rvListActivity.setAdapter(adapterListActivity);
    }

    private double getTotalMoney(ArrayList<EActivity> listActivity){
        double total = 0;
        double calcule = 0;

        for (int i = 0; i < listActivity.size(); i++){
            calcule = (listActivity.get(i).getPrice() * listActivity.get(i).getNumberbox());
            total += calcule;
        }

        return Math.round(total * 100.00) / 100.00;
    }

    private void onClickListener(){
        tilDateStart.setStartIconOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,  (datePicker, i, i1, i2) -> {
                i1 += 1;
                String sDay = i2 < 10? "0" + i2: "" + i2;
                String sMonth = i1 < 10? "0" + i1: "" + i1;
                etDateStart .setText(sDay + "/" + sMonth + "/" + i);
            }, day, month, year);

            //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 0, 0)));
            datePickerDialog.getDatePicker().setMinDate(new Date("2022/01/01").getTime());
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        });
        tilDateEnd.setStartIconOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,  (datePicker, i, i1, i2) -> {
                i1 += 1;
                String sDay = i2 < 10? "0" + i2: "" + i2;
                String sMonth = i1 < 10? "0" + i1: "" + i1;
                etDateEnd .setText(sDay + "/" + sMonth + "/" + i);
            }, day, month, year);

            //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 0, 0)));
            datePickerDialog.getDatePicker().setMinDate(new Date("2022/01/01").getTime());
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        });

        // Filtrar por fechas
        ibFilterActivity.setOnClickListener(view -> {
            String dateStart = etDateStart.getText().toString().trim();
            String dateEnd = etDateEnd.getText().toString().trim();

            if(dateStart.isEmpty() || dateEnd.isEmpty()){
                Toast.makeText(context, "Complete las fechas", Toast.LENGTH_SHORT).show();
            } else{
                filteredByDates(dateStart, dateEnd);
            }
        });

    }

    private void filteredByDates(String dateStart, String dateEnd){
        if(!validateDate(dateStart) || !validateDate(dateEnd)){
            Toast.makeText(context, "Fecha incorrecta", Toast.LENGTH_SHORT).show();
        } else {
            LocalDate lDateStart = adapterListActivity.getLocalDateOfString(dateStart);
            LocalDate lDateEnd = adapterListActivity.getLocalDateOfString(dateEnd);

            if(lDateStart.compareTo(lDateEnd) > 0){
                Toast.makeText(context, "La fecha inicial no puede ser mayor", Toast.LENGTH_SHORT).show();
            }
            else {
                totalMoney =  adapterListActivity.filteredByDate(dateStart, dateEnd);
                tvTotalMoney.setText(String.valueOf(totalMoney));
            }
        }
    }

    public boolean validateDate(String strDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(strDate, formatter);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    private void openBottomSheet(EActivity eActivity){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_update_activity);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // Inicializar referencias con los widgets
        acSearchArea = bottomSheetDialog.findViewById(R.id.acSearchBSActivityUpdate);
        etNumberBox = bottomSheetDialog.findViewById(R.id.etNumberBoxBSActivityUpdate);

        btUpdate = bottomSheetDialog.findViewById(R.id.btUpdateBsActivityUpdate);
        btCancel = bottomSheetDialog.findViewById(R.id.btCloseBsActivityUpdate);

        // Mostrar datos obtenidos
        acSearchArea.setText(eActivity.getNamearea());
        etNumberBox.setText(String.valueOf(eActivity.getNumberbox()));
        idarea = eActivity.getIdarea();

        // Load autocomplete box
        loadAutoCompleteTypeArea();

        // onclick listener
        bottomSheetOnClick(bottomSheetDialog, eActivity);

        bottomSheetDialog.show();
    }

    private void loadAutoCompleteTypeArea(){
        // Obteniendo la lista de areas registradas
        listArea = modelArea.getRows("ASC");

        ArrayAdapter<EArea> adapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listArea);
        acSearchArea.setThreshold(1);
        acSearchArea.setAdapter(adapter);

        acSearchArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listArea.get(i).getIdarea(); // Obteniendo el idarea
                idarea = listArea.get(i).getIdarea();
            }
        });
    }

    private void bottomSheetOnClick(BottomSheetDialog bsDialog, EActivity eActivity){
        btUpdate.setOnClickListener(view1 -> {
            if(etNumberBox.getText().toString().isEmpty()){
                Toast.makeText(context, "Complete los datos", Toast.LENGTH_SHORT).show();
            } else {
                eActivity.setIdarea(idarea);
                eActivity.setNumberbox(Integer.parseInt(etNumberBox.getText().toString()));
                questionUpdate(eActivity);
                bsDialog.dismiss();
            }
        });

        btCancel.setOnClickListener(view1 -> {
            bsDialog.dismiss();
        });
    }

    private void questionUpdate(EActivity eActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("AVANCES");
        builder.setMessage("¿Está seguro de actualizar el registro?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            long idactividad = modelActivity.updateActivity(eActivity);

            if(idactividad > 0){
                loadData();
                Toast.makeText(context, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });

        builder.show();
    }

    private void questionDelete(int idactivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("AVANCES");
        builder.setMessage("¿Está seguro de eliminar el registro?");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            long idval = modelActivity.deleteActivity(idactivity);

            if(idval > 0){
                loadData();
                Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {

        });

        builder.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
    }
}