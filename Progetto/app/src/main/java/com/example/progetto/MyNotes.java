package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;

public class MyNotes extends AppCompatActivity implements View.OnClickListener{

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                break;
        }
    }
}
