package com.example.progetto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AggiungiAppuntiLoc extends AppCompatActivity implements View.OnClickListener{

    private int success;
    private String appuntoTitolo;
    private String appuntoData;
    private String appuntoLink;
    private String appuntomateria;
    private String appuntocontenuto;

    private static String STRING_EMPTY = "";

    final Context ctx=this;
    DataAppLoc da;

    EditText editTitolo, editData, editApp;
    TextView nomemateria;
    Button btnSalva;
    CheckBox checkCondividi;

    String a;

    private static final String BASE_URL = "http://mobileproject.altervista.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi__appunti__loc);

        Intent r = getIntent();
        a = r.getStringExtra("app");

        da = new DataAppLoc(this);

        editTitolo = findViewById(R.id.editTitolo);
        editData = findViewById(R.id.editData);
        editApp = findViewById(R.id.editApp);
        btnSalva = findViewById(R.id.btnSalvaApp);
        btnSalva.setOnClickListener(this);
        checkCondividi = findViewById((R.id.CBcondividi));
        nomemateria = findViewById(R.id.materiaDegliAppunti);

        nomemateria.setText(a);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSalvaApp:
                final String titolo = editTitolo.getText().toString();
                String data = editData.getText().toString();
                String appunti = editApp.getText().toString();
                da.insert(a, data, titolo, appunti);
                //set Result per passare il risultato alla prima activity cosi riesco a riavviarla dalla on result
                String res = "Appunti salvati";
                Intent intent = new Intent();
                intent = intent.putExtra("res", res);
                setResult(Activity.RESULT_OK, intent);

                // Salvo l'appunto nella mamoeria interna del cellulare come file di testo
                Context context = getApplicationContext();
                final String folder = context.getFilesDir().getAbsolutePath() + File.separator + "Appunti/";
                File subFolder = new File(folder);
                if (!subFolder.exists()) {
                    subFolder.mkdirs();
                }
                final String NomeFile = titolo + ".txt";
                FileOutputStream fos = null;
                try {

                    FileOutputStream outputStream = new FileOutputStream(new File(subFolder, NomeFile));
                    outputStream.write(appunti.getBytes());
                    outputStream.close();

                    // Il percorso assoluto di archiviazione è:
                    // data/data/com.example.progetto/files/Appunti/[NomeFile]
                    // data/user/0/com.example.progetto/files/Appunti/[NomeFile]

                    if(checkCondividi.isChecked()){
                        //ora faccio UPLOAD file sul server
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //creating new thread to handle Http Operations

                                uploadFile( folder + NomeFile);
                                //aggiorno il database del server
                                aggiornaAppuntiRemoto();
                            }
                        }).start();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
                break;
        }
    }

    private void aggiornaAppuntiRemoto() {
        if (!STRING_EMPTY.equals(editTitolo.getText().toString()) &&
                !STRING_EMPTY.equals(editData.getText().toString()) &&
                !STRING_EMPTY.equals(nomemateria.getText().toString())) {

            appuntoTitolo = editTitolo.getText().toString();
            appuntoData = editData.getText().toString();
            appuntoLink= "http://mobileproject.altervista.org/Appunti/" + appuntoTitolo + ".txt";
            appuntomateria = nomemateria.getText().toString();
            appuntocontenuto = editApp.getText().toString();

            new AddAppuntoremotoAsyncTask().execute();
        } else {
            Toast.makeText(this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    private class AddAppuntoremotoAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            httpParams.put("appunto_link", appuntoLink);
            httpParams.put("appunto_titolo", appuntoTitolo);
            httpParams.put("appunto_data", appuntoData);
            httpParams.put("appunto_materia", appuntomateria);
            httpParams.put("appunto_contenuto", appuntocontenuto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_appunti.php", "POST", httpParams);
            try {
                success = jsonObject.getInt("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(ctx,
                                "Appunto added on remote DB", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(ctx,
                                "Some error occurred while adding appunto",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }



    //android upload file to server
    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, "Source File Doesn't Exist: " + selectedFilePath, Toast.LENGTH_SHORT).show();
                }
            });
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(BASE_URL + "UploadToServer.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                //Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx, "File Upload completed", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }

            return serverResponseCode;
        }

    }
}
