package com.example.hugolml.pruebaconexionvgingerbread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hugolml on 03/03/2015.
 */
public class Inicio extends ActionBarActivity
{
    private Button Alta;
    private Button Consulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        Alta=(Button)findViewById(R.id.alta);
        Alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrocasa = new Intent(Inicio.this, RegistroCasa.class);
                startActivity(registrocasa);
            }
        });
        Consulta=(Button)findViewById(R.id.consulta);
        Consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consultacasa = new Intent(Inicio.this,Consulta.class);
                startActivity(consultacasa);
            }
        });
    }
}
