package com.ejh.elaweatherapp;

import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView viewdate, viewtemp, viewcity, viewdesc;
    private RequestQueue RQ;
    RadioGroup rdoGroup;
    RadioButton radioButton;
    ImageView viewimage;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewtemp=findViewById(R.id.viewtemp);
        viewcity=findViewById(R.id.viewcity);
        viewdesc=findViewById(R.id.viewdesc);
        viewdate=findViewById(R.id.viewdate);
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
                   // Log.d("Tag", weatherarray.toString()) Log.d("Tag", mainobject.toString());
                    JSONObject object = weatherarray.getJSONObject(0);
                    int temp = (int)Math.round(mainobject.getDouble ("temp"));
                    String temperature = String.valueOf("temp");
                    String description = object.getString("description");
                    String city = response.getString("name");
                    String image = response.getString("icon");
                    viewtemp.setText(temperature);
                    viewdesc.setText(description);
                    viewcity.setText(city);
                    Calendar c =Calendar.getInstance();

                    SimpleDateFormat s = new SimpleDateFormat(" EEEE, MM, DD");
                    String dateformatted = s.format(c.getTime());
                    viewdate.setText(dateformatted);

                    //images
                    String imageUri = "http://openweathermap.org/img/w" + image + "png";
                    viewimage.findViewById(R.id.viewimage);
                    Uri myUri= Uri.parse(imageUri);
                    Picasso.with(MainActivity.this).load(myUri).resize(200, 200).into(viewimage);

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
        //ajouter elements à la queue à la fin
        RQ= Volley.newRequestQueue(this);
        RQ.add(jsonObjectRequest);

        }
    }
