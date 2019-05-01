package com.example.progetto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton clock;
    ImageButton book;
    ImageButton www;
    ImageButton settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        TextView tv = findViewById(R.id.textBenvenuto);
        String w = "Benvenuto "+ getName("nome", this) + "!";
        tv.setText(w);

        clock = (ImageButton) findViewById(R.id.btnClock);
        book = (ImageButton) findViewById(R.id.btnBook);
        www = (ImageButton) findViewById(R.id.btnAppCondivisi);
        settings = (ImageButton) findViewById(R.id.btnSettings);

        clock.setOnClickListener(this);
        book.setOnClickListener(this);
        www.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1.2F, 0.6F);

    public static void setName(String key, String value, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getName(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btnClock:
                view.startAnimation(buttonClick);
                launchClock(view);
                break;
            case R.id.btnBook:
                view.startAnimation(buttonClick);
                launchMyNotes(view);
                break;
            case R.id.btnAppCondivisi:
                view.startAnimation(buttonClick);
                launchShNotes(view);
                break;
            case R.id.btnSettings:
                view.startAnimation(buttonClick);
                launchSettings(view);
                break;
        }
    }

    public void launchSettings(View v)
    {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    public void launchClock(View v)
    {
        Intent i = new Intent (this, Clock.class);
        startActivity(i);
    }

    public void launchMyNotes(View v)
    {
        Intent i = new Intent (this, MyNotes.class);
        startActivity(i);
    }

    public void launchShNotes(View v)
    {
        Intent i = new Intent (this, ShNotes.class);
        startActivity(i);
    }

    public void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();  //Da sostituire con gli Intent
        Log.i(getClass().getName(), message);
    }
}
