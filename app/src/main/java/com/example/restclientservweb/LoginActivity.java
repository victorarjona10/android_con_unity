package com.example.restclientservweb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);


        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Inicializar vistas
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        buttonLogin.setOnClickListener(v -> {
            textView.setText("Cargando...");
            progressBar.setVisibility(View.VISIBLE); // Mostrar el ProgressBar
            textView.setVisibility(View.VISIBLE); // Mostrar el TextView


            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            loginUser(username, password);
        });

        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String user, String password) {
        User u = new User(user, user, password);
        Call<User> call = apiService.loginUser(u);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    u.setUser(response.body());

                    progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                    textView.setVisibility(View.GONE); // Ocultar el TextView
                    saveLoginDetails(u.getUsername(), u.getId());

                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.putExtra("username", u.getUsername());
                    intent.putExtra("idUser", u.getId());
                    intent.putExtra("dinero", u.getDinero());
                    //intent.putExtra("stat_vida", u.getStats().getVida());
                    //intent.putExtra("stat_atq", u.getStats().getAtq());
                    //intent.putExtra("stat_vel", u.getStats().getVel());

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                textView.setVisibility(View.GONE); // Ocultar el TextView

                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginDetails(String username, String idusername) {
        editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("idUser", idusername);
        editor.apply();
    }
}