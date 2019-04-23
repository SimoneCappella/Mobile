package com.example.progetto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.button).setOnClickListener(bh);
        if(MainActivity.getName("nome", this)!= null )
        {
            launchMainActivity();
            finish();
        }
    }

    private class ButtonHandler implements View.OnClickListener
    {
        public void onClick(View v)
        {
            EditText nome = findViewById(R.id.nome);
            MainActivity.setName("nome", nome.getText().toString(),FirstActivity.this);
            Toast.makeText(FirstActivity.this, "Benvenuto " + MainActivity.getName("nome",FirstActivity.this) + "!", Toast.LENGTH_LONG).show();
            launchMainActivity(v);
        }
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
