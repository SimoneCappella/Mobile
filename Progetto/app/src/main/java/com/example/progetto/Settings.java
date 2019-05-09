package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings.ButtonHandler bh = new Settings.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);
        //ButtonHandler bh = new ButtonHandler();
        //findViewById(R.id.btnBack).setOnClickListener(bh);

        TextView tv = findViewById(R.id.modifica_profilo);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchModificaProfilo(v);
            }
        });
    }
    public void launchModificaProfilo(View v)
    {
        Intent i = new Intent (this, ModificaProfilo.class);
        startActivity(i);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            v.startAnimation(buttonClick);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}
