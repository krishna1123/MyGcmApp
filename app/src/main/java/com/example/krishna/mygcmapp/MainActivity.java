package com.example.krishna.mygcmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private android.widget.EditText etReceiverId;
    private android.widget.EditText etMessage;
    private android.widget.Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnSend = (Button) findViewById(R.id.btnSend);
        this.etMessage = (EditText) findViewById(R.id.etMessage);
        this.etReceiverId = (EditText) findViewById(R.id.etSenderId);

        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(etReceiverId.getText().toString().trim(), etMessage.getText().toString().trim());
            }
        });
    }

    private void sendMessage(final String receiverId, final String message){
        Log.d("MainActivity", "sendMessage (Line:35) :"+receiverId+" "+message);

        StringRequest request = new StringRequest(Request.Method.POST, IConst.URL_SEND_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MainActivity", "onResponse (Line:50) :"+response);

                if(response!=null && response.length()!=0){
                    IConst.registered(getApplicationContext(), true);

                    Toast.makeText(getApplicationContext(), "Successfully sended message", Toast.LENGTH_SHORT).show();
                   Log.d("MainActivity", "onResponse (Line:56) :"+"Successfully messaged sended...!");

                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Message Sending failed...!", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "onResponse (Line:63) :"+"Message Sending failed...!1");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Message Failed on our server", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onErrorResponse (Line:73) :"+error.getMessage());
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put(IConst.PARAM_MESSAGE, message);
                hashMap.put(IConst.PARAM_EMAIL, receiverId);
                return hashMap;
            }
        };
        VolleyRequest.getInstance().getRequestQueue(getApplicationContext()).add(request);

    }
}
