package com.example.progetto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivityFirst extends AppCompatActivity implements View.OnClickListener {


    Button signin;
    EditText editNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editNome = findViewById(R.id.username);
        signin = findViewById(R.id.login);

        signin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        MainActivity.setName("nome", editNome.getText().toString(),LoginActivityFirst.this);
        Toast.makeText(LoginActivityFirst.this, "Benvenuto " + MainActivity.getName("nome",LoginActivityFirst.this) + "!", Toast.LENGTH_LONG).show();
        launchMainActivity(v);
        finish();

    }

    private void launchMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}


