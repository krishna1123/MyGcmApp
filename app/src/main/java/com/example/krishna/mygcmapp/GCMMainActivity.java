package com.example.krishna.mygcmapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GCMMainActivity extends ActionBarActivity {

    private EditText etEmail, etName, etPhno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("GCMMainActivity", "onCreate (Line:32) RegId:"+IConst.getRegistrationId(this));
        Log.d("GCMMainActivity", "onCreate (Line:34) isRegistered:"+IConst.isRegistered(this));

        if (IConst.isRegistered(this)) {
            Log.d("GCMMainActivity", "onCreate (Line:37) : Registered...");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.gcm_main_activity);
        init();
    }

    private void init() {
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhno = (EditText) findViewById(R.id.etPhone);
    }

    public void register(View view) {

        String regId=IConst.getRegistrationId(this);

        Log.d("GCMMainActivity", "register (Line:55) :"+regId);
        if(regId!=null && regId.length()!=0){
            IConst.setRegistrationId(getApplicationContext(), regId);
            registerInOurServer(regId);
        }else {

            new AsyncTask<String, String, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    GoogleCloudMessaging googleCloudMessaging = GoogleCloudMessaging.getInstance(getApplicationContext());
                    try {
                        String registrationId = googleCloudMessaging.register(IConst.SENDER_ID);
                        return registrationId;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                protected void onPostExecute(String regId) {
                    super.onPostExecute(regId);
                    Log.d("MainActivity", "onPostExecute 54 " + regId);

                    Toast.makeText(getApplicationContext(), "Registered: " + regId, Toast.LENGTH_SHORT).show();

                    if (regId != null && regId.length() != 0) {
                        IConst.setRegistrationId(getApplicationContext(), regId);
                        registerInOurServer(regId);
                    }
                }
            }.execute();
        }

    }

    private void registerInOurServer(final String registrationId) {
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String phNo = etPhno.getText().toString();

        String url="http://192.168.50.119/workspace/MyGcmApp/register.php?" +
                "email=" +email+
                "&regId=" +IConst.getRegistrationId(getApplicationContext())+
                "&phno=" +phNo+
                "&name="+name;

        url=IConst.URL_REGISTRATION;

        Log.d("GCMMainActivity", "registerInOurServer (Line:112) :"+url);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GCMMainActivity", "onResponse (Line:95) :"+response);

                if(response!=null && response.length()!=0){
                    IConst.registered(getApplicationContext(), true);

                    Toast.makeText(getApplicationContext(), "Successfully registered on Server", Toast.LENGTH_SHORT).show();
                    Log.d("GCMMainActivity", "onResponse (Line:99) :Successfully registered on Server");

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Registration Failed on our server with invalid response", Toast.LENGTH_SHORT).show();
                    Log.d("GCMMainActivity", "onResponse (Line:102) :"+"Registration Failed on our server with invalid response");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Registration Failed on our server", Toast.LENGTH_SHORT).show();
                Log.d("GCMMainActivity", "onErrorResponse (Line:110) :" + "Registration Failed on our server "+error.getMessage());
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put(IConst.PARAM_NAME, name);
                hashMap.put(IConst.PARAM_EMAIL, email);
                hashMap.put(IConst.PARAM_PHONE_NUMBER, phNo);
                hashMap.put(IConst.PARAM_REGISTRATION_ID, registrationId);
                Log.d("GCMMainActivity", "getParams (Line:140) :"+hashMap);
                return hashMap;
            }
        };
        VolleyRequest.getInstance().getRequestQueue(getApplicationContext()).add(request);

    }


}