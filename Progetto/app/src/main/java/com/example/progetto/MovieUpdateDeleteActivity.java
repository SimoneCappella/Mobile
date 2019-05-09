package com.example.progetto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.progetto.helper.CheckNetworkStatus;
import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieUpdateDeleteActivity extends AppCompatActivity {
    private static String STRING_EMPTY = "";

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_MATERIA_ID = "materia_id";
    private static final String KEY_MATERIA_NAME = "materia_name";
    private static final String KEY_APPUNTO_LINK = "appunto_link";
    private static final String KEY_APPUNTO_NAME = "appunto_name";

   private static final String BASE_URL = "http://mobileproject.altervista.org/";


    private String appuntoId;
    private EditText materiaNameEditText;
    private EditText titoloAppunto;
    private EditText linkAppuntoEditText;
    private EditText noteAppuntoEditText;
    private String materiaName;
    private String link;
    private String titoloappunto;
    private String note;
    private Button deleteButton;
    private Button updateButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_update_delete);
        Intent intent = getIntent();
        materiaNameEditText = (EditText) findViewById(R.id.txtMateriaNameUpdate);
        titoloAppunto = (EditText) findViewById(R.id.txtTitoloAppuntoUpdate);
        linkAppuntoEditText = (EditText) findViewById(R.id.txtLinkAppuntoUpdate);
        noteAppuntoEditText = (EditText) findViewById(R.id.txtNoteAppuntoUpdate);

        appuntoId = intent.getStringExtra(KEY_MATERIA_ID);
        new FetchMovieDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateMovie();

                } else {
                    Toast.makeText(MovieUpdateDeleteActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    /**
     * Fetches single movie details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MovieUpdateDeleteActivity.this);
            pDialog.setMessage("Loading Movie Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_MATERIA_ID, appuntoId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_appunto_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject appunto;
                if (success == 1) {
                    //Parse the JSON response
                    appunto = jsonObject.getJSONObject(KEY_DATA);
                    materiaName = appunto.getString(KEY_MATERIA_NAME);
                    link = appunto.getString(KEY_APPUNTO_LINK);
                    titoloappunto = appunto.getString(KEY_APPUNTO_NAME);
                    //note = appunto.getString(KEY_RATING);

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
                    //Populate the Edit Texts once the network activity is finished executing
                    materiaNameEditText.setText(materiaName);
                    linkAppuntoEditText.setText(link);
                    titoloAppunto.setText(titoloappunto);
                    //noteAppuntoEditText.setText(note);

                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MovieUpdateDeleteActivity.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this movie?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(MovieUpdateDeleteActivity.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a movie
     */
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MovieUpdateDeleteActivity.this);
            pDialog.setMessage("Deleting Movie. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_MATERIA_ID, appuntoId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_movie.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(MovieUpdateDeleteActivity.this,
                                "Movie Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(MovieUpdateDeleteActivity.this,
                                "Some error occurred while deleting movie",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateMovie() {


        if (!STRING_EMPTY.equals(materiaNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(linkAppuntoEditText.getText().toString()) &&
                !STRING_EMPTY.equals(titoloAppunto.getText().toString()) &&
                !STRING_EMPTY.equals(noteAppuntoEditText.getText().toString())) {

            materiaName = materiaNameEditText.getText().toString();
            link = linkAppuntoEditText.getText().toString();
            titoloappunto = titoloAppunto.getText().toString();
            note = noteAppuntoEditText.getText().toString();
            new UpdateMovieAsyncTask().execute();
        } else {
            Toast.makeText(MovieUpdateDeleteActivity.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MovieUpdateDeleteActivity.this);
            pDialog.setMessage("Updating Movie. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_MATERIA_ID, appuntoId);
            httpParams.put(KEY_MATERIA_NAME, materiaName);
            httpParams.put(KEY_APPUNTO_LINK, link);
            httpParams.put(KEY_APPUNTO_NAME, titoloappunto);
            //httpParams.put(KEY_NOTE, note);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_movie.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(MovieUpdateDeleteActivity.this,
                                "Movie Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(MovieUpdateDeleteActivity.this,
                                "Some error occurred while updating movie",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}