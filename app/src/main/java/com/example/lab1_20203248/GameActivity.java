package com.example.lab1_20203248;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.lab1_20203248.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private String log = "JUEGO";
    ActivityGameBinding binding;
    private ArrayList<Double> registroJuegos = new ArrayList<>();
    private boolean juegoIniciado = true;
    private ArrayList<String> palabras = new ArrayList<>(Arrays.asList("REDES", "PROPA", "PUCP", "TELITO", "TELECO", "BATI"));
    private String palabraParaAdivinar;
    private int intentosRestantes = 6;
    private Long tiempoInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Button> botones = new ArrayList<>(Arrays.asList(binding.a, binding.b, binding.c, binding.d, binding.e, binding.f, binding.g,
                binding.h, binding.i, binding.j, binding.k, binding.l, binding.m, binding.n,
                binding.o, binding.p, binding.q, binding.r, binding.s, binding.t, binding.u,
                binding.v, binding.w, binding.x, binding.y, binding.z));

        binding.buttonNuevoJuego.setOnClickListener(view -> {
            resetearVariables();
            for (Button button: botones){
                button.setEnabled(true);
            }
            iniciarJuego();
        });

        for (Button button: botones){
            button.setOnClickListener(view -> {
                actualizarGuiones(button);
            });
        }

        iniciarJuego();
    }

    public void iniciarJuego(){
        tiempoInicio = System.currentTimeMillis();

        Random random = new Random();
        String palabra = palabras.get(random.nextInt(palabras.size()));
        palabraParaAdivinar = palabra;
        String guionesIniciales = generarGuiones(palabra);
        binding.palabra.setText(guionesIniciales);
        Log.d(log, "palabra: "+ palabraParaAdivinar);
    }

    public String generarGuiones(String palabra){
        String guiones = "";
        for (int i = 0; i < palabra.length(); i++){
            guiones = guiones+"_";
        }
        return guiones;
    }

    public void actualizarGuiones(Button button){

        if (juegoIniciado){

            button.setEnabled(false);
            String letraSeleccionada = button.getText().toString();
            StringBuilder guiones = new StringBuilder(binding.palabra.getText().toString());

            int index = palabraParaAdivinar.indexOf(letraSeleccionada);
            if (index >= 0){ // acierto
                while (index >= 0){
                    guiones.setCharAt(index, letraSeleccionada.charAt(0));
                    binding.palabra.setText(guiones);
                    index = palabraParaAdivinar.indexOf(letraSeleccionada, index+1);
                }
            }
            else{ // error
                --intentosRestantes;
                switch (intentosRestantes){
                    case 5:
                        binding.cabeza.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        binding.torso.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        binding.brazoDerecho.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        binding.brazoIzquierdo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        binding.piernaIzquierda.setVisibility(View.VISIBLE);
                        break;
                }
            }

            if (guiones.toString().equals(palabraParaAdivinar)){
                long tiempoFin = System.currentTimeMillis();
                double tiempo = (tiempoFin - tiempoInicio)/1000.0;
                registroJuegos.add(tiempo);
                juegoIniciado = false;
                binding.estadoJuego.setText("Ganó / Terminó en "+tiempo+" seg");
            } else if (intentosRestantes == 0) {
                binding.piernaDerecha.setVisibility(View.VISIBLE);
                registroJuegos.add(0.00);
                juegoIniciado = false;
                binding.estadoJuego.setText("Perdió");
            }
        }
    }

    public void resetearVariables(){
        juegoIniciado = true;
        palabraParaAdivinar = null;
        intentosRestantes = 6;
        tiempoInicio = null;

        binding.estadoJuego.setText("");
        binding.cabeza.setVisibility(View.INVISIBLE);
        binding.torso.setVisibility(View.INVISIBLE);
        binding.brazoDerecho.setVisibility(View.INVISIBLE);
        binding.brazoIzquierdo.setVisibility(View.INVISIBLE);
        binding.piernaIzquierda.setVisibility(View.INVISIBLE);
        binding.piernaDerecha.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        getSupportActionBar().setTitle("TeleAhorcado");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.estadisticas){
            Intent intent = new Intent(GameActivity.this, StatisticsActivity.class);
            intent.putExtra("registroJuegos", registroJuegos);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}