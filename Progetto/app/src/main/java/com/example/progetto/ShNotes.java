package com.example.progetto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto.helper.CheckNetworkStatus;
import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ShNotes extends AppCompatActivity implements View.OnClickListener{

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_MATERIA_ID = "materia_id";
    private static final String KEY_MATERIA_NAME = "materia_name";
    private static final String BASE_URL = "http://mobileproject.altervista.org/";
    private ArrayList<HashMap<String, String>> movieList;
    private ListView materieListView;
    private ProgressDialog pDialog;

    ImageButton back;
    ImageButton filtra;

    final Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condivisi_listing);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        filtra = (ImageButton) findViewById(R.id.filtra);
        filtra.setOnClickListener(this);

        materieListView = (ListView) findViewById(R.id.materieList);
        new FetchMoviesAsyncTask().execute();




        // opppure
        /*
        DataManager mydatabase=new DataManager(ctx);
        String[] fromColumns={"m"};
        int[] toViews = {R.id.nomemateria};
        final Cursor cursor = mydatabase.selectAll();
        final SimpleCursorAdapter arrayAdapter = new SimpleCursorAdapter(this,R.layout.modellorigamaterie,cursor,fromColumns,toViews,0);
        materieListView = (ListView) findViewById(R.id.materieList);
        materieListView.setAdapter(arrayAdapter);
        materieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent i = new Intent(ctx, AppuntiListingActivity.class);
                //startActivity(i);
                ///fino qui è ok


                String nomeMateria = ((TextView) view.findViewById(R.id.nomemateria))
                        .getText().toString();
                Intent intent = new Intent(getApplicationContext(),
                        AppuntiListingActivity.class);
                intent.putExtra(KEY_MATERIA_NAME, nomeMateria);
                startActivityForResult(intent, 22);


                finish();//finishing activity

            }
        });
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 22) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }


    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ShNotes.this);
            pDialog.setMessage("Loading materie. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_materie.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    movieList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer materiaId = movie.getInt(KEY_MATERIA_ID);
                        String materiaName = movie.getString(KEY_MATERIA_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_MATERIA_ID, materiaId.toString());
                        map.put(KEY_MATERIA_NAME, materiaName);
                        movieList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    populateMovieList();
                }
            });
        }

    }

    private void populateMovieList() {
        ListAdapter adapter = new SimpleAdapter(
                ShNotes.this, movieList,
                R.layout.modellorigamaterie, new String[]{KEY_MATERIA_ID,
                KEY_MATERIA_NAME},
                new int[]{R.id.materiacondivisaID, R.id.nomemateria});
        // updating listview
        materieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        materieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    String nomeMateria = ((TextView) view.findViewById(R.id.nomemateria))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            AppuntiListingActivity.class);
                    intent.putExtra(KEY_MATERIA_NAME, nomeMateria);
                    startActivityForResult(intent, 22);

                } else {
                    Toast.makeText(ShNotes.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

    }












    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                break;
            case R.id.filtra:

                finish();
                break;
        }
    }

}
