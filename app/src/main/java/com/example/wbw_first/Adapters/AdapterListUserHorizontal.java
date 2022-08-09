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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdapterListUserHorizontal extends RecyclerView.Adapter<AdapterListUserHorizontal.ViewHolder> {

    // Definición de variables (objetos)
    private ArrayList<EUser> listUser;
    private ArrayList<EUser> listOriginal;
    private LayoutInflater mInflater;
    private Context context;
    private AdapterListUserHorizontal.OnItemClickListener onClickListener;

    /**
     * Interfaz permite controlar el evento click de los botones
     */
    public interface OnItemClickListener {
        void onItemClickCard(EUser eUser);
    }

    /**
     * Está clase Adapter recibe el Contexto y un Array List de tipo EUser
     * que contiene los elementos a mostrar en el Recycler view
     * @param context
     * @param listUser
     */
    public AdapterListUserHorizontal(Context context,  ArrayList<EUser> listUser, AdapterListUserHorizontal.OnItemClickListener onClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listUser = listUser;
        this.onClickListener = onClickListener;

        this.listOriginal = new ArrayList<>();
        this.listOriginal.addAll(listUser);
    }


    /**
     * Realizar busqueda de usuario
     * @param search
     */
    public void filteredUser(String search){
        if(search.isEmpty()){
            listUser.clear();
            listUser.addAll(listOriginal);
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                ArrayList<EUser> collections = (ArrayList<EUser>) listOriginal.stream()
                        .filter(i -> i.getNameuser().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());

                listUser.clear();
                listUser.addAll(collections);
            }
            else {
                listUser.clear();

                for (EUser eUser: listOriginal){
                    if (eUser.getNameuser().toLowerCase().contains(search)){
                        listUser.add(eUser);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterListUserHorizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_user_progress, null);
        return new AdapterListUserHorizontal.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListUserHorizontal.ViewHolder holder, int position) {
        holder.setBinData(listUser.get(position));
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    // Inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declaración de variables
        ImageView ivPicture;
        TextView tvName, tvLastname;
        CardView cvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar referencias
            ivPicture = itemView.findViewById(R.id.ivPictureUserProgress);
            tvName = itemView.findViewById(R.id.tvNameUserCardProgress);
            tvLastname = itemView.findViewById(R.id.tvLastnameCardProgress);
            cvItem = itemView.findViewById(R.id.cvItemCardProgress);
        }

        @SuppressLint("ResourceType")
        public void setBinData(EUser eUser) {
            ivPicture.findViewById(R.drawable.avatar);
            tvName.setText(eUser.getNameuser());
            tvLastname.setText(eUser.getLastname());
            cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickCard(eUser);
                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
        }
    }
}
