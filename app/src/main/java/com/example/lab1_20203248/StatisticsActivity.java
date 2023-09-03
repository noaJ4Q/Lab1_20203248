package com.example.lab1_20203248;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        for (int i = 0; i < registroJuegos.size(); i++){
            LinearLayout linearLayout = binding.linearLayoutEstadisticas;
            TextView textView = new TextView(StatisticsActivity.this);
            if (registroJuegos.get(i) != 0.00){
                textView.setText("Juego "+(i+1)+": Terminó en "+registroJuegos.get(i)+" seg");
            }
            else{
                textView.setText("Juego "+(i+1)+": Canceló");
            }
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            float escala = getResources().getDisplayMetrics().density;
            int dp = (int) (10*escala+0.5f); // px a dp
            textView.setPadding(dp,dp,dp,dp);
            linearLayout.addView(textView);
            Log.d("JUEGO", String.valueOf(registroJuegos.get(i)));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}