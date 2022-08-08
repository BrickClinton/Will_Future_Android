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

import com.example.wbw_first.DataBase.ModelUser;
import com.example.wbw_first.Entities.EUser;
import com.example.wbw_first.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapterCard extends RecyclerView.Adapter<ListAdapterCard.ViewHolder> {

    // Definición de variables (objetos)
    private ArrayList<EUser> listUser;
    private ArrayList<EUser> listOriginal;
    private LayoutInflater mInflater;
    private Context context;
    private ListAdapterCard.OnItemClickListener onClickListener;

    /**
     * Interfaz permite controlar el evento click de los botones
     */
    public interface OnItemClickListener {
        void onItemClickEdit(EUser eUser);
        void onItemClickDelete(EUser eUser);
    }

    /**
     * Está clase Adapter recibe el Contexto y un Array List de tipo EUser
     * que contiene los elementos a mostrar en el Recycler view
     * @param context
     * @param listUser
     */
    public ListAdapterCard(Context context,  ArrayList<EUser> listUser, ListAdapterCard.OnItemClickListener onClickListener) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_user, null);
        return new ListAdapterCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterCard.ViewHolder holder, int position) {
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
        Button btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar referencias
            ivPicture = itemView.findViewById(R.id.ivPictureUser);
            tvName = itemView.findViewById(R.id.tvNameUserCard);
            tvLastname = itemView.findViewById(R.id.tvLastnameCard);
            btEdit = itemView.findViewById(R.id.btEditDataUser);
            btDelete = itemView.findViewById(R.id.btDeleteUser);
        }

        @SuppressLint("ResourceType")
        public void setBinData(EUser eUser) {
            ivPicture.findViewById(R.drawable.avatar);
            tvName.setText(eUser.getNameuser());
            tvLastname.setText(eUser.getLastname());
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickEdit(eUser);

                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onClickListener.onItemClickDelete(eUser);
                    } catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
        }
    }


}
