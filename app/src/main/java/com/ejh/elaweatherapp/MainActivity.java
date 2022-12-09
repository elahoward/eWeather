package com.ejh.elaweatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
    String maVille="Toronto";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewtemp=findViewById(R.id.viewtemp);
        viewcity=findViewById(R.id.viewcity);
        viewdesc=findViewById(R.id.viewdesc);
        viewdate=findViewById(R.id.viewdate);
        rdoGroup=findViewById(R.id.rdoGroup);
        afficher ();
      /*  rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
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
        }); */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.recherche, menu);
        MenuItem menuItem= menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nom de la ville");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                maVille=query;
                afficher();
                //clavier regulating
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
              if (getCurrentFocus()!=null){
                  inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
              }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
                return super.onCreateOptionsMenu(menu);
    }


    public void afficher (){

        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId)
            {
                radioButton=findViewById(checkedId);
                switch (radioButton.getId())
                {
                    case R.id.Imperial:
                        url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appId=8202c79f5d3d03e92863ebbf770b93d9&units=imperial";
                        break;
                    case R.id.Metric:
                        url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appId=8202c79f5d3d03e92863ebbf770b93d9&units=metric";
                        break;
                }
            }
        });
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainobject = response.getJSONObject("main");
                    JSONObject sysobject = response.getJSONObject("sys");
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

                    SimpleDateFormat s = new SimpleDateFormat(" EEEE, MM, dd");
                    String dateformatted = s.format(c.getTime());
                    viewdate.setText(dateformatted);

                    /*images
                    String imageUri = "http://openweathermap.org/img/w" + image + "png";
                    viewimage.findViewById(R.id.viewimage);
                    Uri myUri= Uri.parse(imageUri);
                    Picasso.with(MainActivity.this).load(myUri).resize(200, 200).into(viewimage);
                    */
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
