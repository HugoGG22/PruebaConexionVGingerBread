package com.example.hugolml.pruebaconexionvgingerbread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText usuario;
    private EditText pass;
    private Button validar;
    private Button registro;

    // Progress Dialog

    private ProgressDialog pDialog;

            // JSON parser class

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://10.0.2.2:8080/BienesRaices/loginJson.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.Usuario);
        pass = (EditText) findViewById(R.id.Contrasena);
        validar = (Button) findViewById(R.id.Btnvalidar);
        registro = (Button) findViewById(R.id.Btnregistro_usuario);
        validar.setOnClickListener(this);
        registro.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Btnvalidar:

                new AttemptLogin().execute();

                break;

            case R.id.Btnregistro_usuario:

                Intent i = new Intent(this, RegistroUsuario.class);

                startActivity(i);

                break;

            default:

                break;

        }

    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Conectando con el servidor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override

        protected String doInBackground(String... args) {

            // TODO Auto-generated method stub

            // Check for success tag

            int success;

            String username = usuario.getText().toString();
            String password = pass.getText().toString();

            try {

                // Building Parameters

                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("usuario", username));
                params.add(new BasicNameValuePair("pass", password));
                Log.d("request!", "starting");

                // getting product details by making HTTP request

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // check your log for json response

                Log.d("Login attempt", json.toString());

                // json success tag

                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Log.d("Login Exitoso!", json.toString());
                    Intent i = new Intent(MainActivity.this, ReadComments.class);
                    finish();
                    startActivity(i);

                    return json.getString(TAG_MESSAGE);

                }else{

                    Log.d("Error en el Login!", json.getString(TAG_MESSAGE));

                    return json.getString(TAG_MESSAGE);

                }

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return null;

        }

                /**

                 * After completing background task Dismiss the progress dialog

                 * **/

        protected void onPostExecute(String file_url) {

            // dismiss the dialog once product deleted

            pDialog.dismiss();

            if (file_url != null){

                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();

            }

        }

    }

}
