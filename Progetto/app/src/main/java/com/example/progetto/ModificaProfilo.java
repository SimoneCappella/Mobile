package com.example.progetto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class ModificaProfilo extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_profilo);

        //getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.btnBack).setOnClickListener(bh);

        TextView modUser = findViewById(R.id.modifica_user);
        TextView modPass = findViewById(R.id.modifica_pass);

        TextView utente = findViewById(R.id.showUser);
        utente.setText(FirstActivity.login_name);
        TextView password = findViewById(R.id.showPassword);
        password.setText(FirstActivity.login_pass);


        modUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogUser();
            }
        });

        modPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogPass();
            }
        });
    }



    public void alertDialogUser()                                                                   //Dialog
    {
        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("AlertDialog di MrWebMaster");                                        //titolo del dialog
        miaAlert.setMessage("Questa è la mia prima AlertDialog");                               //messaggio del dialog
        AlertDialog alert = miaAlert.create();
        alert.show();
    }

    public void alertDialogEmail()                                                                   //Dialog
    {
        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("AlertDialog di MrWebMaster");                                        //titolo del dialog
        miaAlert.setMessage("Questa è la mia prima AlertDialog");                               //messaggio del dialog
        AlertDialog alert = miaAlert.create();
        alert.show();
    }

    public void alertDialogPass()                                                                   //Dialog
    {
        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("AlertDialog di MrWebMaster");                                        //titolo del dialog
        miaAlert.setMessage("Questa è la mia prima AlertDialog");                               //messaggio del dialog
        AlertDialog alert = miaAlert.create();
        alert.show();
    }


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            v.startAnimation(buttonClick);
            finish();
        }
    }
}

