package com.example.progetto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    ListView lv;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        lv = findViewById(R.id.listview);


        final ArrayList<String> al = new ArrayList<>();

        al.add("Modifica Username");
        al.add("Modifica Password");
        al.add("About Us");

        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(arrayadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        launchEditUser(view);
                        break;
                    case 1:
                        launchEditPass(view);
                        break;
                    case 2:
                        break;
                }

            }
        });
    }

    public void launchEditUser(View v)
    {
        Intent i = new Intent(this , EditUser.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void launchEditPass(View v)
    {
        Intent i = new Intent(this , editPass.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    /*private void show (String message)
    {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    private class UpdatePasswordAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            String userPrecedente= FirstActivity.login_name;
            httpParams.put("username", userPrecedente);
            httpParams.put("password", PasswordNuova);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "aggiorna_password.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    //se ho modificato la password riavvio l'app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }
    }

    private class DeleteUserAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            String userPrecedente= FirstActivity.login_name;
            httpParams.put("username", userPrecedente);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "elimina_utente.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    //se ho modificato l'utente riavvio l'app
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            v.startAnimation(buttonClick);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }*/
}
