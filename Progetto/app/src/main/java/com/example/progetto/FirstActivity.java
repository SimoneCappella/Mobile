package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.example.progetto.MainActivity.username;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editNome, editPass;
    public static String login_name, login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));


        editNome = findViewById(R.id.lgnusername);
        editPass = findViewById(R.id.lgnpassword);
        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
        Button register = findViewById(R.id.btnRegistrami);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnLogin:
                userLogin(view);
                break;
            case R.id.btnRegistrami:
                launchReg(view);
                break;
        }
    }

    public void launchMainActivity(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void launchReg(View view)
    {
        Intent i = new Intent(this , Register.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideright);
    }
    public void userLogin(View view) {
        login_name = editNome   .getText().toString();
        login_pass = editPass.getText().toString();
        String method = "login";
        String auth="";
        SupportTask supportTask = new SupportTask(FirstActivity.this);
        try {
            auth = supportTask.execute(method, login_name, login_pass).get();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }catch (InterruptedException a)
        {
            a.printStackTrace();
        }
        if(auth.equals("    Login Success  "))
        {
            launchMainActivity(view);
            username = login_name;
            show("Benvenuto "+ login_name + "!");
            finish();
        }else
        {
            show("Dati errati. Riprova.");
        }
    }
    private void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}


