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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Settings extends AppCompatActivity {

    private String PasswordNuova;
    final Context ctx=this;
    ImageButton back;
    private static final String BASE_URL = "http://mobileproject.altervista.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings.ButtonHandler bh = new Settings.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);

        TextView tvModUser = findViewById(R.id.modifica_username);
        TextView tvModPassword = findViewById(R.id.modifica_password);

        tvModPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(ctx);
                final View promptsView=li.inflate(R.layout.modifica_password, null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(ctx);
                alertDialogBuilder.setView(promptsView);

                EditText UtenteAttuale = promptsView.findViewById(R.id.Username);
                UtenteAttuale.setText(FirstActivity.login_name);

                alertDialogBuilder.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nuovapassword = promptsView.findViewById(R.id.nuovaPassword);
                        EditText confermapassword = promptsView.findViewById(R.id.confermaPassword);
                        String nuovaPassword = nuovapassword.getText().toString();
                        String confermaPassword = confermapassword.getText().toString();
                        if (nuovaPassword.equals(confermaPassword)){
                            if (nuovaPassword.equals("")){
                                show("Inserire una password");
                            }else{
                                PasswordNuova=nuovaPassword;
                                new UpadtePasswordAsyncTask().execute();
                                show("Password aggiornata");
                            }
                        }else{
                            show("Le password non coincidono!");
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialogBuilder.setCancelable(false);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        tvModUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater li=LayoutInflater.from(ctx);
                final View promptsView=li.inflate(R.layout.modifica_username, null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(ctx);
                alertDialogBuilder.setView(promptsView);

                EditText VecchioUtente = promptsView.findViewById(R.id.Username);
                VecchioUtente.setText(FirstActivity.login_name);

                alertDialogBuilder.setPositiveButton("Salva", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //salvo le nuove credenziali sul db remoto
                        EditText utente = promptsView.findViewById(R.id.nuovaPassword);
                        EditText password = promptsView.findViewById(R.id.confermaPassword);
                        String register_name = utente.getText().toString();
                        String register_pass = password.getText().toString();

                        if(register_name.equals("")||(register_pass.equals(""))){
                            show("Inserire username e password !");
                        }else{
                            String method = "register";
                            String auth="";
                            SupportTask supportTask = new SupportTask(ctx);
                            try {
                                auth = supportTask.execute(method, register_name, register_pass).get();
                            } catch (ExecutionException e)
                            {
                                e.printStackTrace();
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            if (auth.equals("    Registrazione avvenuta con successo!  "))
                            {
                                show("Registrazione effettuata con successo!");
                                //elimino l'utente vecchio
                                new DeleteUserAsyncTask().execute();
                                show("Effettua di nuovo il login!");

                            }else if (auth.equals("    Username in uso  "))
                            {
                                show("Username già in uso, prova con uno diverso!");
                            } else
                            {
                                show("Errore, riprovare più tardi!");
                            }
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
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

    private void show (String message)
    {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    private class UpadtePasswordAsyncTask extends AsyncTask<String, String, String> {

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
    }
}
