package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnProsegui;

    TextView textInserisciNome;

    EditText editNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        btnProsegui = (Button) findViewById(R.id.btnProsegui);
        textInserisciNome = (TextView) findViewById(R.id.textInserisciNome);
        editNome = (EditText) findViewById(R.id.editNome);
        btnProsegui.setOnClickListener(this);

      /*  if(MainActivity.getName("nome", this)!= null )
        {
            launchMainActivity();
            finish();
        }*/
    }

    public void onClick(View v)
    {
        MainActivity.setName("nome", editNome.getText().toString(),FirstActivity.this);
        Toast.makeText(FirstActivity.this, "Benvenuto " + MainActivity.getName("nome",FirstActivity.this) + "!", Toast.LENGTH_LONG).show();
        launchMainActivity(v);
    }

    public void launchMainActivity(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void launchMainActivity()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}