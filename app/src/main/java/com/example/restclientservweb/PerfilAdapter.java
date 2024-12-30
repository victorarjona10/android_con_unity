package com.example.restclientservweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<String> parameters;


    public PerfilAdapter(Context context, List<String> parameters) {
        this.parameters = parameters;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PerfilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_perfil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilAdapter.ViewHolder holder, int position) {
        String a = parameters.get(position);
        switch (position) {
            case 0:
                holder.txtView.setText("Nombre: " + a);
                break;
            case 1:
                holder.txtView.setText("Dinero: " + a + "€");
                break;
            case 2:
                holder.txtView.setText("Puntos: "+a);
                break;
            default:
                holder.txtView.setText(" ");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return parameters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtView;
        ViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtView);
        }
    }
}
