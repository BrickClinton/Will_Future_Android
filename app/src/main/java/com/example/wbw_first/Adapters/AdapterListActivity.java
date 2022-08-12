package com.example.wbw_first.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wbw_first.Entities.EActivity;
import com.example.wbw_first.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
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
     * @param dateStart
     * @param dateEnd
     */
    public double filteredByDate(String dateStart, String dateEnd){
        if(dateStart.isEmpty() || dateEnd.isEmpty()){
            listActivity.clear();
            listActivity.addAll(listOriginal);
        }
        else {
            LocalDate lDateStart = getLocalDateOfString(dateStart);
            LocalDate lDateEnd = getLocalDateOfString(dateEnd);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                ArrayList<EActivity> collections = (ArrayList<EActivity>) listOriginal.stream()
                        .filter(i ->
                                (getLocalDate(i.getDateregister()).compareTo(lDateStart) > 0 || getLocalDate(i.getDateregister()).compareTo(lDateStart) == 0) &&
                                (getLocalDate(i.getDateregister()).compareTo(lDateEnd) < 0 || getLocalDate(i.getDateregister()).compareTo(lDateEnd) == 0))
                        .collect(Collectors.toList());

                listActivity.clear();
                listActivity.addAll(collections);
            }
            else {
                listActivity.clear();

                for (EActivity eActivity: listOriginal){
                    LocalDate date = getLocalDate(eActivity.getDateregister());
                    boolean dateInit = (date.compareTo(lDateStart) > 0 || date.compareTo(lDateStart) == 0);
                    boolean dateFin = (date.compareTo(lDateEnd) < 0 || date.compareTo(lDateEnd) == 0);

                    if(dateInit && dateFin){
                        listActivity.add(eActivity);
                    }
                }
            }
        }

        notifyDataSetChanged();
        return getTotalMoney(listActivity);
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

    public LocalDate getLocalDateOfString(String dateStr){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            return localDate;

        } catch (Exception ex){
            throw new RuntimeException(ex.toString());
        }
    }

    private LocalDate getLocalDate(String localDateTimeStr){
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    private String getDateTime(String dateTimeISO){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeISO);
        return dtf.format(localDateTime);
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
            String datetime = getDateTime(eActivity.getDateregister());

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
