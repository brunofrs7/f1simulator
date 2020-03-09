package com.example.f1simulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mViewHolder.bt_menu_circuito = findViewById(R.id.bt_menu_circuito);
        mViewHolder.bt_menu_piloto = findViewById(R.id.bt_menu_piloto);
        mViewHolder.bt_menu_equipa = findViewById(R.id.bt_menu_equipa);
        mViewHolder.bt_menu_simulador = findViewById(R.id.bt_menu_simulador);

        mViewHolder.bt_menu_circuito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mViewHolder.bt_menu_piloto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ListaPilotosActivity.class);
                startActivity(i);
            }
        });
        mViewHolder.bt_menu_equipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mViewHolder.bt_menu_simulador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class ViewHolder {
        Button bt_menu_piloto, bt_menu_circuito, bt_menu_equipa, bt_menu_simulador;
    }
}
