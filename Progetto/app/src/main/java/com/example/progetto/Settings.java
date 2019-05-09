package com.example.progetto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import android.widget.ImageButton;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }





}
