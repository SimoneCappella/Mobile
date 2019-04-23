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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        TextView tv = findViewById(R.id.benvenuto);
        String w = "Benvenuto "+ getName("nome", this) + "!";
        tv.setText(w);

        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.clock).setOnClickListener(bh);
        findViewById(R.id.book).setOnClickListener(bh);
        findViewById(R.id.www).setOnClickListener(bh);
        findViewById(R.id.settings).setOnClickListener(bh);

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

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            switch(view.getId())
            {
                case R.id.clock:
                    view.startAnimation(buttonClick);
                    show("Si apre l'orario");
                    break;
                case R.id.book:
                    view.startAnimation(buttonClick);
                    show("Si aprono i miei appunti");
                    break;
                case R.id.www:
                    view.startAnimation(buttonClick);
                    show("Si aprono gli appunti condivisi");
                    break;
                case R.id.settings:
                    view.startAnimation(buttonClick);
                    show("Si aprono le impostazioni");
                    break;
            }
        }
    }
    /*public void launchOrario(View v)
    {
        Intent i = new Intent(this, Orario.class);
        startActivity(i);
    }

    public void launchImieiAppunti(View v)
    {
        Intent i = new Intent(this, ImieiAppunti.class);
        startActivity(i);
    }

    public void launchAppuntiCondivisi(View v)
    {
        Intent i = new Intent(this, AppuntiCondivisi.class);
        startActivity(i);
    }

    public void launchSettings(View v)
    {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }*/


    public void launchFirstActivity()
    {
        Intent i = new Intent(this, FirstActivity.class);
        startActivity(i);
    }
    void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();  //Da sostituire con gli Intent
        Log.i(getClass().getName(), message);
    }
}
