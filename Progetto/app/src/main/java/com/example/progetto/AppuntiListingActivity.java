package com.example.progetto;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class AppuntiListingActivity extends AppCompatActivity implements View.OnClickListener {

    final Context ctx=this;

    private static final int WRITE_REQUEST_CODE = 300;
    private String url;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_MATERIA_ID = "materia_id";
    private static final String KEY_MATERIA_NAME = "materia_name";
    private static final String KEY_APPUNTO_LINK = "appunto_link";
    private static final String KEY_APPUNTO_TITOLO = "appunto_titolo";
    private static final String KEY_APPUNTO_DATA = "appunto_data";

    private static final String BASE_URL = "http://mobileproject.altervista.org/";

    private ArrayList<HashMap<String, String>> appuntiList;
    private ListView appuntiListView;
    private ProgressDialog pDialog;
    private String materiaName;
    private TextView MateriaAppunti;

    private String nomeLink;
    private String fileName;


    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appunti_listing);
        appuntiListView = (ListView) findViewById(R.id.appuntilist);

        MateriaAppunti = findViewById(R.id.MateriaDegliAppunti);

        Intent intent = getIntent();
        materiaName = intent.getStringExtra(KEY_MATERIA_NAME);

        new FetchMoviesAsyncTask().execute();
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

    }



    /**
     * Fetches the list of movies from the server
     */
    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
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
            httpParams.put(KEY_MATERIA_NAME, materiaName);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "fetch_appunti.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray appunti;
                if (success == 1) {
                    appuntiList = new ArrayList<>();
                    appunti = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < appunti.length(); i++) {
                        JSONObject Appunto = appunti.getJSONObject(i);
                        Integer movieId = Appunto.getInt(KEY_MATERIA_ID);
                        String appuntoTitolo = Appunto.getString(KEY_APPUNTO_TITOLO);
                        String appuntoLink = Appunto.getString(KEY_APPUNTO_LINK);
                        String appuntoData = Appunto.getString(KEY_APPUNTO_DATA);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_MATERIA_ID, movieId.toString());
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
                R.layout.list_item, new String[]{KEY_MATERIA_ID,
                KEY_APPUNTO_TITOLO,KEY_APPUNTO_LINK,KEY_APPUNTO_DATA},
                new int[]{R.id.appuntocondivisoID, R.id.appuntotitolo,R.id.appuntoLink,R.id.appuntoData});
        // updating listview
        appuntiListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        appuntiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /*
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.materiacondivisaID))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            MovieUpdateDeleteActivity.class);
                    intent.putExtra(KEY_MATERIA_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(AppuntiListingActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }*/
                return false;
            }
        });

        appuntiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nomeLink = ((TextView) view.findViewById(R.id.appuntoLink)).getText().toString();
                //Extract file name from URL
                fileName = nomeLink.substring(nomeLink.lastIndexOf('/') + 1, nomeLink.length());

                LayoutInflater li=LayoutInflater.from(ctx);
                View promptsView=li.inflate(R.layout.messaggio, null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(ctx);
                alertDialogBuilder.setView(promptsView);
                TextView testoFileName = (TextView) promptsView.findViewById(R.id.nomefile);
                testoFileName.setText(fileName);


                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //scarico il file del link
                        if (CheckForSDCard.isSDCardPresent()) {

                            //check if app has permission to write to the external storage.
                            if (EasyPermissions.hasPermissions(AppuntiListingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                new DownloadFile().execute(nomeLink);
                                //qui dovrei aggiornare il database locale
                                //ma non ho il contenuto dell'appunto

                            } else {
                                //If permission is not present request for the same.
                                EasyPermissions.requestPermissions(AppuntiListingActivity.this, getString(R.string.write_file), WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                            }


                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "SD Card not found", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.setCancelable(false);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
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

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                //fileName = timestamp + "_" + fileName;

                //External directory path to save file

                folder = Environment.getExternalStorageDirectory() + File.separator + "Appunti/";

                //Create folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
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
