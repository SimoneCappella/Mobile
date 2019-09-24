package com.example.progetto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    String register_name, register_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        Button registrami = findViewById(R.id.btnReg);
        registrami.setOnClickListener(this);
        username = findViewById(R.id.editUser);
        password = findViewById(R.id.editPsw);
    }

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btnReg:
                userRegister();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private void show (String message)
    {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    public void userRegister()
    {
        register_name = username.getText().toString();
        register_pass = password.getText().toString();
        String method = "register";
        String auth="";
        SupportTask supportTask = new SupportTask(Register.this);
        //Controllo risposta database
        if (register_name.length() >= 3 && register_pass.length() >= 3)
        {
            try {
                String url = "http://mobileproject.altervista.org/register.php";
                auth = supportTask.execute(method, register_name, register_pass, url).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (auth.equals("Registrazione avvenuta con successo!")) {
                show("Registrazione effettuata con successo, accedi!");
                finish();
            } else if (auth.equals("Username in uso")) {
                show("Username già in uso, prova con uno diverso!");
            }
        }
        else
        {
            show("I campi devono contenere almeno 3 caratteri.");
        }
    }
}
