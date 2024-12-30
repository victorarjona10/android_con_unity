package com.example.restclientservweb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreActivity extends AppCompatActivity {
    private ApiService apiService;
    private ListView listViewProducts;
    private ListView listViewComprados;
    private TextView usernameDisplay;
    private TextView dineroDisplay;
    private String username;
    private String idUser;
    private int dinero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        listViewProducts = findViewById(R.id.listViewProducts);
        listViewComprados = findViewById(R.id.listViewComprados);
        usernameDisplay = findViewById(R.id.usernameDisplay);
        dineroDisplay = findViewById(R.id.dineroDisplay);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        username = getIntent().getStringExtra("username");
        idUser = getIntent().getStringExtra("idUser");
        if (username != null) {
            usernameDisplay.setText("Usuario: " + username);
            getDinero();
        }
        else
        {
            dinero = 20;
            dineroDisplay.setText("Dinero: " + dinero + "€");
        }


        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, MenuActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            startActivity(intent);
        });
        getProducts();
        getProductsOfUser();

        // Inicializar el adaptador para la lista de productos comprados
        ProductAdapter purchasedAdapter = new ProductAdapter(this, new ArrayList<>(), username, true, this.dinero, idUser);
        listViewComprados.setAdapter(purchasedAdapter);
        listViewComprados.setVisibility(View.VISIBLE);
        findViewById(R.id.compradosHeader).setVisibility(View.VISIBLE);



    }

    private void getProducts() {
        Call<List<Products>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> products = response.body();
                   // ArrayAdapter<Products> adapter = new ArrayAdapter<>(StoreActivity.this, android.R.layout.simple_list_item_1, products);
                    ProductAdapter adapter = new ProductAdapter(StoreActivity.this, products, username, false, dinero, idUser);
                    listViewProducts.setAdapter(adapter);
                } else {
                    Toast.makeText(StoreActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDinero() {
        Call<Integer> call = apiService.getDinero(idUser);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    dinero = response.body();

                    dineroDisplay.setText("Dinero: " + dinero + "€");
                } else {
                    Toast.makeText(StoreActivity.this, "Failed to retrieve dinero", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                Log.d("CAT", "getDinero error: "+t.getMessage());
            }
        });
    }
    private void getProductsOfUser(){
        Call<List<Products>> call = apiService.getProductsOfUser(idUser);

        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> products = response.body();
                    ProductAdapter adapter = new ProductAdapter(StoreActivity.this, products, username, true, dinero, idUser);
                    listViewComprados.setAdapter(adapter);
                } else {
                    Toast.makeText(StoreActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(StoreActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //actualiza el dinero restante
    public void updateDineroDisplay(int nuevoDinero) {
        dinero = nuevoDinero;
        dineroDisplay.setText("Dinero: " + dinero + "€");
    }
}