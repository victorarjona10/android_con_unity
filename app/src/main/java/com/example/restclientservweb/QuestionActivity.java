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

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionActivity extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        EditText editTextSender = findViewById(R.id.editTextSender);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        Button buttonBackToMain = findViewById(R.id.button_return);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/") // Cambiado a 10.0.2.2 para el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        buttonSubmit.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = sdf.format(new Date());
            String titulo = editTextTitle.getText().toString();
            String mensaje = editTextMessage.getText().toString();
            String nombre = editTextSender.getText().toString();
            Question question = new Question(fecha,titulo, mensaje, nombre);
            sendQuestion(question);
        });
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void sendQuestion(Question question) {
        Call<Question> call = apiService.sendQuestion(question);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                Toast.makeText(QuestionActivity.this, "Información enviada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "Error al enviar la pregunta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}