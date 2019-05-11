package com.example.progetto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AggiungiAppuntiLoc extends AppCompatActivity implements View.OnClickListener{

    final Context ctx=this;
    DataAppLoc da;

    EditText editTitolo, editData, editApp;
    Button btnSalva;
    CheckBox checkCondividi;
    private String folder;

    String a;

    private static final String BASE_URL = "http://mobileproject.altervista.org/UploadToServer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_appunti_loc);

        Intent r = getIntent();
        a = r.getStringExtra("app");

        da = new DataAppLoc(this);

        editTitolo = findViewById(R.id.editTitolo);
        editData = findViewById(R.id.editData);
        editApp = findViewById(R.id.editApp);
        btnSalva = findViewById(R.id.btnSalvaApp);
        btnSalva.setOnClickListener(this);
        checkCondividi = findViewById((R.id.CBcondividi));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSalvaApp:
                final String titolo = editTitolo.getText().toString();
                String data = editData.getText().toString();
                String appunti = editApp.getText().toString();
                da.insert(a, data, titolo, appunti);
                //set Result per passare il risultato alla prima activitu cosi riesco a riavviarla dalla on result
                String res = "appunti salvati";
                Intent intent = new Intent();
                intent = intent.putExtra("res", res);
                setResult(Activity.RESULT_OK, intent);

                if (checkCondividi.isChecked()) {
                    //se il chekbox CONDIVIDI è selezionato, devo salvare l'appunto sul server
                    if (CheckForSDCard.isSDCardPresent()) {
                        //External directory path to save file
                        folder = Environment.getExternalStorageDirectory() + File.separator + "Appunti/";
                        //Create androiddeft folder if it does not exist
                        File directory = new File(folder);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }
                        try {
                            FileOutputStream fos = new FileOutputStream(
                                    new File(getExternalFilesDir("Appunti/"), titolo + ".txt"));
                            //new File(getExternalFilesDir(folder), titolo + ".txt"));
                            fos.write(appunti.getBytes());
                            fos.close();
                            Toast.makeText(this,
                                    "Data written in external storage",
                                    Toast.LENGTH_SHORT).show();
                            //ora faccio UPLOAD file sul server
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations

                                    uploadFile("/storage/emulated/0/Android/data/com.example.progetto/files/Appunti/" +titolo + ".txt");
                                    //uploadFile("com.example.progetto/files/Appunti/" +titolo + ".txt");
                                    //uploadFile("Appunti/" +titolo + ".txt");
                                    //uploadFile(folder+titolo + ".txt");
                                }
                            }).start();
                            //aggiungo l'appunto sul DB remoto


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(this,
                                "External Storage is not Available or is Read Only",
                                Toast.LENGTH_LONG).show();

                    }
                }

                finish();
                break;
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
                URL url = new URL(BASE_URL);
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
