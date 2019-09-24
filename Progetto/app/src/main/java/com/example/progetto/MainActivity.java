package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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
    String username;
    public static int width;
    public static int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        TextView tv = findViewById(R.id.textBenvenuto);
        username = FirstActivity.login_name;
        String w = "Benvenuto "+ username + "!";
        tv.setText(w);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try{
            display.getRealSize(size);
        }catch(NoSuchMethodError err){
            display.getSize(size);
        }
        width = size.x;
        height = size.y;
        clock = findViewById(R.id.btnClock);
        book = findViewById(R.id.btnBook);
        www = findViewById(R.id.btnAppCondivisi);
        settings = findViewById(R.id.btnSettings);
        clock.setOnClickListener(this);
        book.setOnClickListener(this);
        www.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1.2F, 0.6F);

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
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void launchClock(View v)
    {
        Intent i = new Intent (this, Clock.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideright);
    }

    public void launchMyNotes(View v)
    {
        Intent i = new Intent (this, MyNotes.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void launchShNotes(View v)
    {
        Intent i = new Intent (this, ShNotes.class);
        startActivity(i);
        overridePendingTransition(R.anim.slideleft, R.anim.slideright);
    }

    public void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.i(getClass().getName(), message);
    }

    public void setUsername(String user)
    {
        username = user;
    }
    
    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
