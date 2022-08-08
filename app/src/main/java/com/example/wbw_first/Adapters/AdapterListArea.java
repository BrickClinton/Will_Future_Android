package com.example.wbw_first.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wbw_first.Entities.EArea;
import com.example.wbw_first.Interfaces.IOnClickListenerGeneric;
import com.example.wbw_first.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdapterListArea extends RecyclerView.Adapter<AdapterListArea.ViewHolder> {

    // Definición de variables (objetos)
    private ArrayList<EArea> listArea;
    private ArrayList<EArea> listOriginal;
    private LayoutInflater mInflater;
    private Context context;
    private IOnClickListenerGeneric onClickListener;

    /**
     * Está clase Adapter recibe el Contexto y un Array List de tipo EUser
     * que contiene los elementos a mostrar en el Recycler view
     * @param context
     * @param listArea
     */
    public AdapterListArea(Context context,  ArrayList<EArea> listArea, IOnClickListenerGeneric<EArea> onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listArea = listArea;
        this.onClickListener = onClickListener;

        this.listOriginal = new ArrayList<>();
        this.listOriginal.addAll(listArea);
    }


    /**
     * Realizar busqueda de usuario
     * @param search
     */
    public void filteredUser(String search){
        if(search.isEmpty()){
            listArea.clear();
            listArea.addAll(listOriginal);
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                ArrayList<EArea> collections = (ArrayList<EArea>) listOriginal.stream()
                        .filter(i -> i.getNamearea().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());

                listArea.clear();
                listArea.addAll(collections);
            }
            else {
                listArea.clear();

                for (EArea eArea: listOriginal){
                    if (eArea.getNamearea().toLowerCase().contains(search)){
                        listArea.add(eArea);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterListArea.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_area, null);
        return new AdapterListArea.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setBinData(listArea.get(position));
    }

    @Override
    public int getItemCount() {
        return listArea.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declaración de variables
        TextView tvName, tvPrice;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar referencias
            tvName = itemView.findViewById(R.id.tvNameAreaList);
            tvPrice = itemView.findViewById(R.id.tvPriceAreaList);
            btEdit = itemView.findViewById(R.id.ivEditArea);
            btDelete = itemView.findViewById(R.id.ivDeleteArea);
        }

        @SuppressLint("ResourceType")
        public void setBinData(EArea eArea) {
            tvName.setText(eArea.getNamearea());
            tvPrice.setText(String.valueOf(eArea.getPrice()));
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickEdit(eArea);
                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickDelete(eArea);
                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
        }
    }
}
