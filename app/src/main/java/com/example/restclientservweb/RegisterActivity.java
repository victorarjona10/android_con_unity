package com.example.restclientservweb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        deleteLoginDetails();

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextPassword2 = findViewById(R.id.editTextPassword2);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        // Inicializar vistas
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        buttonRegister.setOnClickListener(v -> {

            textView.setText("Cargando...");
            progressBar.setVisibility(View.VISIBLE); // Mostrar el ProgressBar
            textView.setVisibility(View.VISIBLE); // Ocultar el TextView


            String email = editTextEmail.getText().toString();
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String password2 = editTextPassword2.getText().toString();
            if(password.equals(password2)) {
                registerUser(email, username, password);
            }
            else{
                Toast.makeText(RegisterActivity.this,
                        "Passwords are different", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String email, String username, String password) {
        User user = new User(email, username, password);
        Call<User> call = apiService.registerUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                    textView.setVisibility(View.GONE); // Ocultar el TextView

                    user.setUser(response.body());
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, StoreActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("idUser", user.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                textView.setVisibility(View.GONE); // Ocultar el TextView
                Toast.makeText(RegisterActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteLoginDetails() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}