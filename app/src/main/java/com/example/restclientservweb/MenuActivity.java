package com.example.restclientservweb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    private String username;
    private String idUser;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        sharedPreferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);

        username = getIntent().getStringExtra("username");
        idUser = getIntent().getStringExtra("idUser");


        Button buttonBackToMain = findViewById(R.id.btnLogOut);
        buttonBackToMain.setOnClickListener(v -> {
            deleteLoginDetails();
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            startActivity(intent);
        });
    }

    public void click_btnTienda(View v){
        Intent intent = new Intent(MenuActivity.this, StoreActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);

        startActivity(intent);
    }

    public void click_btnPerfil(View v){
        Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);

        startActivity(intent);
    }

    private void deleteLoginDetails() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}