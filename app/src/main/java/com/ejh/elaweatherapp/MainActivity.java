package com.ejh.elaweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    TextView date, temp, city, desc;
    private RequestQueue RQ;

    ImageView image;
    String ville= "Toronto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RQ= Volley.newRequestQueue(this);
        date=findViewById(R.id.date);
        temp=findViewById(R.id.temp);
        city=findViewById(R.id.city);
        desc=findViewById(R.id.desc);
        afficher();

    }

    public void afficher (){

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appId=8202c79f5d3d03e92863ebbf770b93d9&units=metric";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainobject = response.getJSONObject("main");
                    JSONArray weatherarray = response.getJSONArray("weather");
                    Log.d("Tag", weatherarray.toString());
                    System.out.println(weatherarray);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RQ.add(jsonObjectRequest);

        }
    }
