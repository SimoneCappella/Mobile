package com.example.progetto;

import android.content.Intent;
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
        Button registrami = findViewById(R.id.btnReg);
        registrami.setOnClickListener(this);
        username = findViewById(R.id.editUser);
        password = findViewById(R.id.editPsw);
    }

    public void onClick (View v)
    {
        userRegister();
        finish();
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
            show("Registrazione effettuata con successo, accedi!");
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else if (auth.equals("    Username in uso  "))
            {
            show("Username già in uso, prova con uno diverso!");
            launchReg();
            } else
            {
                show("Errore, riprovare più tardi!");
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
    }

    public void launchReg()
    {
        Intent i = new Intent(this , Register.class);
        startActivity(i);
    }
}
