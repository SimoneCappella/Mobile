package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class MyNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        MyNotes.ButtonHandler bh = new MyNotes.ButtonHandler();

        findViewById(R.id.back).setOnClickListener(bh);
        findViewById(R.id.btnAgg).setOnClickListener(bh);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.back:
                    v.startAnimation(buttonClick);
                    finish();
                    break;
                case R.id.btnAgg:
                    v.startAnimation(buttonClick);
                    launchAgg(v);
                    break;
            }

        }
    }

    public void launchAgg(View v){
        Intent i = new Intent(this, AggMaterie.class);
        startActivity(i);
    }
}
