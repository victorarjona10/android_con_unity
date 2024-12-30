package com.example.restclientservweb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductPerfilAdapter extends RecyclerView.Adapter<ProductPerfilAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<Products> products;


    public ProductPerfilAdapter(Context context, List<Products> products) {
        this.products = products;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ProductPerfilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_perfil, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = products.get(position);
        holder.txtView.setText(product.getName());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtView;
        ViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtView);
        }
    }
}
