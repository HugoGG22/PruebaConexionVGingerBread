package com.example.hugolml.pruebaconexionvgingerbread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugolml on 03/03/2015.
 */
public class Consulta extends ActionBarActivity

{
    ListView listaJson;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta);

        listaJson=(ListView)findViewById(R.id.Lista);
        Tarea1 tarea1= new Tarea1();
        tarea1.cargarContenido(getApplicationContext());
        tarea1.execute(listaJson);


    }

    static class Tarea1 extends AsyncTask<ListView,Void,ArrayAdapter<DatosCasa>>
    {
        Context contexto;
        ListView list;
        InputStream is;
        ArrayList<DatosCasa> ListaDatosCasa= new ArrayList<DatosCasa>();

        public void cargarContenido(Context contexto)
        {

            this.contexto=contexto;

        }
        @Override
        protected ArrayAdapter<DatosCasa> doInBackground(ListView... params) {
            list= params[0];
            String resultado= "";
            DatosCasa casa;

            // CREAR CONEXION HTTP
            HttpClient cliente = new DefaultHttpClient();
            HttpPost peticionPost= new HttpPost("http://10.0.2.2:8080/BienesRaices/ConsultaCasaJson.php");

            try
            {
                HttpResponse response = cliente.execute(peticionPost);
                HttpEntity contenido= response.getEntity();
                is=contenido.getContent();


            }
            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader buferlector=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            String linea= null;


            try
            {
                while((linea=buferlector.readLine())!=null)
                {
                    sb.append(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultado=sb.toString();

            try
            {
                JSONArray arrayJson=new JSONArray(resultado);
                for(int i =0; i<arrayJson.length(); i++)
                {
               JSONObject objectJson=arrayJson.getJSONObject(i);
               casa=new DatosCasa(objectJson.getInt("IdCasa"),objectJson.getString("Calle"),objectJson.getString("Colonia"),objectJson.getString("Ciudad"),objectJson.getString("Para"),objectJson.getString("Tipo"),objectJson.getString("Monto"));
               ListaDatosCasa.add(casa);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<DatosCasa> adaptador=new ArrayAdapter<DatosCasa>(contexto,android.R.layout.simple_list_item_1,ListaDatosCasa);

            return adaptador;
        }

        @Override
        protected void onPostExecute(ArrayAdapter<DatosCasa> result) {
            list.setAdapter(result);
        }
    }





}
