package com.example.lab1_20203248;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.lab1_20203248.databinding.ActivityStatisticsBinding;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    ActivityStatisticsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Double> registroJuegos = (ArrayList<Double>) getIntent().getSerializableExtra("registroJuegos");
        for (Double tiempo: registroJuegos){
            Log.d("JUEGO", String.valueOf(tiempo));
        }

        binding.buttonNuevoJuego.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle("TeleAhorcado");
        return true;
    }
}