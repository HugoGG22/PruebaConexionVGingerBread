package com.example.hugolml.pruebaconexionvgingerbread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Hugolml on 27/02/2015.
 */
public class RegistroUsuario extends ActionBarActivity
{
    private EditText Nombre;
    private EditText ApePa;
    private EditText ApeMa;
    private EditText Telefono;
    private EditText Facebook;
    private EditText usuario;
    private EditText contrasena;
    private TextView Fecha;
    private Button insertar;
    private Button RegistroCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.registro_usuario);

        Fecha=(TextView)findViewById(R.id.fechar);

        final Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);



        // set current date into textview
        Fecha.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(yy).append("").append("-").append(mm + 1).append("-").append(dd));

        Nombre=(EditText)findViewById(R.id.Nombre);
        ApePa=(EditText)findViewById(R.id.AP);
        ApeMa=(EditText)findViewById(R.id.AM);
        Telefono=(EditText)findViewById(R.id.Tel);
        Facebook=(EditText)findViewById(R.id.Face);
        usuario=(EditText)findViewById(R.id.Usuario);
        contrasena=(EditText)findViewById(R.id.Contrasena);



        insertar=(Button)findViewById(R.id.insertar);
        insertar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if     (!usuario.getText().toString().trim().equalsIgnoreCase("")||
                        !contrasena.getText().toString().trim().equalsIgnoreCase("")||
                        !Nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !ApePa.getText().toString().trim().equalsIgnoreCase("")||
                        !ApeMa.getText().toString().trim().equalsIgnoreCase("")||
                        !Telefono.getText().toString().trim().equalsIgnoreCase("")||
                        !Facebook.getText().toString().trim().equalsIgnoreCase(""))
                {
                    new Insertar(RegistroUsuario.this).execute();
                    Intent registro = new Intent(RegistroUsuario.this, Inicio.class);
                    startActivity(registro);
                }


                else
                    Toast.makeText(RegistroUsuario.this, "Hay informacion por llenar", Toast.LENGTH_LONG).show();


            }

        });


    }


    //Inserta los datos de las Personas en el servidor.
    private boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://10.0.2.2:8080/BienesRaices/RegistrarUsuario.php"); // Url del Servidor
        //A�adimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(8);
        nameValuePairs.add(new BasicNameValuePair("usuario",usuario.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("contrasena",contrasena.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Nombre",Nombre.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("ApePa",ApePa.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("ApeMa",ApeMa.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Telefono",Telefono.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Facebook",Facebook.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("Fecha",Fecha.getText().toString()));


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


    //AsyncTask para insertar Personas
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
                        Toast.makeText(context, "BIENVENIDO", Toast.LENGTH_LONG).show();
                        usuario.setText("");
                        contrasena.setText("");
                        Nombre.setText("");
                        ApePa.setText("");
                        ApeMa.setText("");
                        Telefono.setText("");
                        Facebook.setText("");

                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Usuario no insertado con exito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}
