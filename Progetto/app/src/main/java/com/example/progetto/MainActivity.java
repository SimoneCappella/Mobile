package com.example.progetto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#999999"));

        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.clock).setOnClickListener(bh);
        findViewById(R.id.book).setOnClickListener(bh);
        findViewById(R.id.www).setOnClickListener(bh);
        findViewById(R.id.settings).setOnClickListener(bh);

    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

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

    void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.i(getClass().getName(), message);
    }
}
