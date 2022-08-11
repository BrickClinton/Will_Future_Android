package com.example.wbw_first.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.number.Precision;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wbw_first.Entities.EActivity;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class AdapterListActivity extends RecyclerView.Adapter<AdapterListActivity.ViewHolder>{
    // Definición de variables (objetos)
    private ArrayList<EActivity> listActivity;
    private ArrayList<EActivity> listOriginal;
    private LayoutInflater mInflater;
    private Context context;
    private AdapterListActivity.OnItemClickListener onClickListener;

    /**
     * Interfaz permite controlar el evento click de los botones
     */
    public interface OnItemClickListener {
        void onItemClickEdit(EActivity eActivity);
        void onItemClickDelete(EActivity eActivity);
    }

    /**
     * Está clase Adapter recibe el Contexto y un Array List de tipo EUser
     * que contiene los elementos a mostrar en el Recycler view
     * @param context
     * @param listActivity
     */
    public AdapterListActivity(Context context,  ArrayList<EActivity> listActivity, AdapterListActivity.OnItemClickListener onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listActivity = listActivity;
        this.onClickListener = onClickListener;

        this.listOriginal = new ArrayList<>();
        this.listOriginal.addAll(listActivity);
    }


    /**
     * Realizar busqueda de usuario
     * @param datestart
     * @param dateend
     */
    public void filteredByDate(String datestart, String dateend){
    }

    @NonNull
    @Override
    public AdapterListActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_activity, null);
        return new AdapterListActivity.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.setBinData(listActivity.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listActivity.size();
    }

    // Inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declaración de variables
        TextView tvDatetime, tvArea, tvPrice, tvNumberBox, tvTotal;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar referencias
            tvDatetime = itemView.findViewById(R.id.tvDatetimeCardActivity);
            tvArea = itemView.findViewById(R.id.tvNameAreaCardActivity);
            tvPrice = itemView.findViewById(R.id.tvPriceAreaCardActivity);
            tvNumberBox = itemView.findViewById(R.id.tvNumberBoxCardActivity);
            tvTotal = itemView.findViewById(R.id.tvTotalCardActivity);

            btEdit = itemView.findViewById(R.id.ivEditActivity);
            btDelete = itemView.findViewById(R.id.ivDeleteActivity);
        }

        @SuppressLint("ResourceType")
        public void setBinData(EActivity eActivity) throws ParseException {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(eActivity.getDateregister());
            String datetime = dtf.format(localDateTime);

            double total = (eActivity.getPrice() * eActivity.getNumberbox());
            double round = Math.round(total * 100.00)/100.00;

            tvDatetime.setText(datetime);
            tvArea.setText(String.valueOf(eActivity.getNamearea()));
            tvNumberBox.setText(String.valueOf(eActivity.getNumberbox()));
            tvPrice.setText(String.valueOf(eActivity.getPrice()));
            tvTotal.setText(String.valueOf(round));

            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickEdit(eActivity);

                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickDelete(eActivity);
                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
        }
    }
}
