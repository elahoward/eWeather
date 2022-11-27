package com.ejh.elaweatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup rdoGroup;
    RadioButton radioButton;
    ImageView image;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RQ= Volley.newRequestQueue(this);
        temp=findViewById(R.id.temp);
        city=findViewById(R.id.city);
        rdoGroup=findViewById(R.id.rdoGroup);

        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId)
            {
                radioButton=findViewById(checkedId);
                switch (radioButton.getId())
                {
                    case R.id.Imperial:
                        url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appId=8202c79f5d3d03e92863ebbf770b93d9&units=imperial";
                        afficher();
                        break;
                    case R.id.Metric:
                        url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appId=8202c79f5d3d03e92863ebbf770b93d9&units=metric";
                        afficher();
                        break;
                }
            }
        });
    }

    public void afficher (){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainobject = response.getJSONObject("main");
                    JSONArray weatherarray = response.getJSONArray("weather");
                    Log.d("Tag", weatherarray.toString());
                    Log.d("Tag", mainobject.toString());

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
