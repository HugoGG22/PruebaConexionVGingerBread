package com.example.hugolml.pruebaconexionvgingerbread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugolml on 01/03/2015.
 */
public class RegistroCasa extends ActionBarActivity implements AdapterView.OnItemSelectedListener
{
   private Spinner Ciudad;
   private EditText Calle;
   private EditText Colonia;
   private EditText Monto;
   private RadioGroup RG;
   private RadioButton Renta;
   private RadioButton Venta;
   int idrb;
   String id;
   private Spinner Tipo;
   private Button Aceptar;
   private Button Cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.registro_casa);


        Ciudad=(Spinner)findViewById(R.id.ciudad);
        Calle=(EditText)findViewById(R.id.calle);
        Colonia=(EditText)findViewById(R.id.colonia);
        Renta=(RadioButton)findViewById(R.id.renta);
        Venta=(RadioButton)findViewById(R.id.venta);
        RG=(RadioGroup)findViewById(R.id.Para);
        Tipo=(Spinner)findViewById(R.id.tipo);
        Monto=(EditText)findViewById(R.id.monto);

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.renta)
                {
                    idrb=RG.getCheckedRadioButtonId();
                    id=Integer.toString(idrb);
                    id="Renta";
                }

                else
                {
                    id="Venta";
                }

            }
        });

        Aceptar=(Button)findViewById(R.id.Aceptar);
        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if     (!Calle.getText().toString().trim().equalsIgnoreCase("")||
                        !Colonia.getText().toString().trim().equalsIgnoreCase("")||
                        !Monto.getText().toString().trim().equalsIgnoreCase(""))

                {
                    new Insertar(RegistroCasa.this).execute();

                }


                else
                    Toast.makeText(RegistroCasa.this, "Hay informacion por llenar", Toast.LENGTH_LONG).show();


            }

        });


        ArrayAdapter adapter1=ArrayAdapter.createFromResource(this,R.array.ciudad,android.R.layout.simple_spinner_item);
        Ciudad.setAdapter(adapter1);
        Ciudad.setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) this);

        ArrayAdapter adapter2=ArrayAdapter.createFromResource(this,R.array.Tipo,android.R.layout.simple_spinner_item);
        Tipo.setAdapter(adapter2);
        Tipo.setOnItemSelectedListener((android.widget.AdapterView.OnItemSelectedListener) this);
    }


    //Inserta los datos de las casas en el servidor.
    private boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://10.0.2.2:8080/BienesRaices/RegistrarCasa.php"); // Url del Servidor
        //A�adimos nuestros datos

        nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair("Calle",Calle.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Colonia",Colonia.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Ciudad",Ciudad.getSelectedItem().toString()));
        nameValuePairs.add(new BasicNameValuePair("RG",id));
        nameValuePairs.add(new BasicNameValuePair("Tipo",Tipo.getSelectedItem().toString()));
        nameValuePairs.add(new BasicNameValuePair("Monto",Monto.getText().toString()));


        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //AsyncTask para insertar casa
    class Insertar extends AsyncTask<String,String,String> {

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Casa registrada con exito", Toast.LENGTH_LONG).show();
                        Calle.setText("");
                        Colonia.setText("");
                        Monto.setText("");

                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Error al registrar casa", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}

