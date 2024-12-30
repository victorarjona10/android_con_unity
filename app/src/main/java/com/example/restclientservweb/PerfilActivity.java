package com.example.restclientservweb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {

    private ApiService apiService;
    private String username;
    private String idUser;
    private Integer dinero = -1;
    private String email;
//private int puntos = -1;
    private List<Products> productos;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        username = getIntent().getStringExtra("username");
        idUser = getIntent().getStringExtra("idUser");
        //email = getIntent().getStringExtra("email");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        getDinero();
        getProductsOfUser();

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewProducts = findViewById(R.id.recyclerView);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
    }

    public void click_btnOK(View v){
        Intent intent = new Intent(PerfilActivity.this, MenuActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);
       // intent.putExtra("email", email);
        startActivity(intent);
    }

    public void startAdapters(){
           if ( dinero != -1 && username != null ) {
                List<String> parameters = new ArrayList<>();
                parameters.add(username);
                parameters.add(String.valueOf(dinero));
                //parameters.add(email);


                PerfilAdapter adapter = new PerfilAdapter(PerfilActivity.this, parameters);
                recyclerView.setAdapter(adapter);

                ProductPerfilAdapter adapter2 = new ProductPerfilAdapter(PerfilActivity.this, productos);
                recyclerViewProducts.setAdapter(adapter2);
            } else returnToMenu();

    }
    private void getDinero() {
        Call<Integer> call = apiService.getDinero(idUser);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    dinero = response.body();


                    //getPuntos();

                } else {
                    returnToMenu();

                    Toast.makeText(PerfilActivity.this, "Failed to retrieve dinero", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                Log.d("CAT", "getDinero error: "+t.getMessage());
                returnToMenu();
            }
        });
    }



//    public void getPuntos(){
//        Call<Integer> call = apiService.getPuntos(idUser);
//        call.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    puntos = response.body();
//                    getProductsOfUser();
//                } else {
//                    returnToMenu();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable throwable) {
//                returnToMenu();
//
//            }
//        });
//    }

    private void getProductsOfUser(){
        Call<List<Products>> call = apiService.getProductsOfUser(idUser);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    productos = response.body();
                    startAdapters();
                } else {
                    returnToMenu();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                returnToMenu();
            }
        });
    }


        public void returnToMenu(){
        Intent intent = new Intent(PerfilActivity.this, MenuActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);
        Toast.makeText(PerfilActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}