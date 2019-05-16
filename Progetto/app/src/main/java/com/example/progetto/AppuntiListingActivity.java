package com.example.progetto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppuntiListingActivity extends AppCompatActivity implements View.OnClickListener {

    final Context ctx=this;

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_APPUNTO_ID = "materia_id";
    private static final String KEY_APPUNTO_MATERIA = "materia_name";
    private static final String KEY_APPUNTO_LINK = "appunto_link";
    private static final String KEY_APPUNTO_TITOLO = "appunto_titolo";
    private static final String KEY_APPUNTO_DATA = "appunto_data";
    private static final String KEY_APPUNTO_CONTENUTO = "appunto_contenuto";

    private static final String BASE_URL = "http://mobileproject.altervista.org/";

    private ArrayList<HashMap<String, String>> appuntiList;
    private ListView appuntiListView;
    private ProgressDialog pDialog;
    private String materiaName;
    private TextView MateriaAppunti;

    private String nomeLink;
    private String titoloAppunto;
    private String fileName;

    private String appuntoMateria;
    private String appuntoData;
    private String appuntoContenuto;

    ImageButton back;

    DataAppLoc da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appunti_listing);
        appuntiListView = (ListView) findViewById(R.id.appuntilist);

        MateriaAppunti = findViewById(R.id.MateriaDegliAppunti);

        Intent intent = getIntent();
        materiaName = intent.getStringExtra(KEY_APPUNTO_MATERIA);

        new FetchAppuntiListAsyncTask().execute();
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        da = new DataAppLoc(this);

    }



    /**
     * Fetches the list of appunti from the server
     */
    private class FetchAppuntiListAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AppuntiListingActivity.this);
            pDialog.setMessage("Loading appunti. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_APPUNTO_MATERIA, materiaName);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_appunti.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray appunti;
                if (success == 1) {
                    appuntiList = new ArrayList<>();
                    appunti = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate appunti list
                    for (int i = 0; i < appunti.length(); i++) {
                        JSONObject Appunto = appunti.getJSONObject(i);
                        Integer appuntoID = Appunto.getInt(KEY_APPUNTO_ID);
                        String appuntoTitolo = Appunto.getString(KEY_APPUNTO_TITOLO);
                        String appuntoLink = Appunto.getString(KEY_APPUNTO_LINK);
                        String appuntoData = Appunto.getString(KEY_APPUNTO_DATA);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_APPUNTO_ID, appuntoID.toString());
                        map.put(KEY_APPUNTO_TITOLO, appuntoTitolo);
                        map.put(KEY_APPUNTO_LINK, appuntoLink);
                        map.put(KEY_APPUNTO_DATA, appuntoData);

                        appuntiList.add(map);
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
                    populateAppuntiList();
                }
            });
        }

    }

     /**
     * Updating parsed JSON data into ListView
     * */
    private void populateAppuntiList() {

        MateriaAppunti.setText(materiaName);

        ListAdapter adapter = new SimpleAdapter(
                AppuntiListingActivity.this, appuntiList,
                R.layout.list_item, new String[]{KEY_APPUNTO_ID,
                KEY_APPUNTO_TITOLO,KEY_APPUNTO_LINK,KEY_APPUNTO_DATA},
                new int[]{R.id.appuntocondivisoID, R.id.appuntotitolo,R.id.appuntoLink,R.id.appuntoData});
        // updating listview
        appuntiListView.setAdapter(adapter);

        appuntiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

        appuntiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                titoloAppunto = ((TextView) view.findViewById(R.id.appuntotitolo)).getText().toString();
                nomeLink = ((TextView) view.findViewById(R.id.appuntoLink)).getText().toString();
                //Extract file name from URL
                fileName = nomeLink.substring(nomeLink.lastIndexOf('/') + 1, nomeLink.length());

                LayoutInflater li=LayoutInflater.from(ctx);
                View promptsView=li.inflate(R.layout.messaggio, null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(ctx);
                alertDialogBuilder.setView(promptsView);
                TextView testoFileName = (TextView) promptsView.findViewById(R.id.nomefile);
                testoFileName.setText(fileName);


                alertDialogBuilder.setNeutralButton("Visualizza", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //leggo i campi dal database remoto
                        new FetchAppuntiDetails().execute();
                    }
                });

                alertDialogBuilder.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.setNegativeButton("Salva", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DownloadFile().execute(nomeLink);
                        new AggiornaDB().execute();

                    }
                });

                alertDialogBuilder.setCancelable(false);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    private class AggiornaDB extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AppuntiListingActivity.this);
            pDialog.setMessage("Aggiorno database locale. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_APPUNTO_TITOLO, titoloAppunto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "get_appunto_details.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray appunti;
                if (success == 1) {
                    appunti = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate appunti list
                    for (int i = 0; i < appunti.length(); i++) {
                        JSONObject Appunto = appunti.getJSONObject(i);

                        Integer appuntoID = Appunto.getInt(KEY_APPUNTO_ID);
                        appuntoMateria = Appunto.getString(KEY_APPUNTO_MATERIA);
                        appuntoData = Appunto.getString(KEY_APPUNTO_DATA);
                        String appuntoLink = Appunto.getString(KEY_APPUNTO_LINK);
                        appuntoContenuto = Appunto.getString(KEY_APPUNTO_CONTENUTO);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();

            Cursor nc;
            nc = da.searchTitolo(titoloAppunto);
            int num = nc.getCount();
            if(nc.getCount()<=0){
                //Se l'appunto non è presente nel database locale, lo aggiungo
                da.insert(appuntoMateria, appuntoData, titoloAppunto, appuntoContenuto);
                //set Result per passare il risultato alla prima activity cosi riesco a riavviarla dalla on result
                String res = "Appunti salvati";
                Intent intent = new Intent();
                intent = intent.putExtra("res", res);
                setResult(Activity.RESULT_OK, intent);
            }else{
                //Se c'è già un appunto colo nome 'titoloAppunto' non lo aggiungo
                Toast.makeText(getApplicationContext(), "Appunto " + titoloAppunto + " già presente nel database locale", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class FetchAppuntiDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AppuntiListingActivity.this);
            pDialog.setMessage("Loading dettagli. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_APPUNTO_TITOLO, titoloAppunto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "get_appunto_details.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray appunti;
                if (success == 1) {
                    appunti = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate appunti list
                    for (int i = 0; i < appunti.length(); i++) {
                        JSONObject Appunto = appunti.getJSONObject(i);

                        Integer appuntoID = Appunto.getInt(KEY_APPUNTO_ID);
                        appuntoMateria = Appunto.getString(KEY_APPUNTO_MATERIA);
                        appuntoData = Appunto.getString(KEY_APPUNTO_DATA);
                        String appuntoLink = Appunto.getString(KEY_APPUNTO_LINK);
                        appuntoContenuto = Appunto.getString(KEY_APPUNTO_CONTENUTO);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();

            //visualizzo i campi nell'activity VediAppunti
            Intent i = new Intent(getApplication(), VediAppunti.class);
            Bundle bundle = new Bundle();
            bundle.putString("materia", appuntoMateria);
            bundle.putString("titolo", titoloAppunto);
            bundle.putString("data", appuntoData);
            bundle.putString("app", appuntoContenuto);
            i.putExtra("data", bundle);
            startActivity(i);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the appunto.
            // So refresh the appunto listing
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                break;
        }
    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(AppuntiListingActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                //String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                //fileName = timestamp + "_" + fileName;

                // Salvo l'appunto nella memoria interna del cellulare come file di testo
                Context context = getApplicationContext();
                final String folder = context.getFilesDir().getAbsolutePath() + File.separator + "Appunti/";
                File subFolder = new File(folder);
                if (!subFolder.exists()) {
                    subFolder.mkdirs();
                }
                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

                return "Downloaded at: " + folder + fileName;




            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }

}
