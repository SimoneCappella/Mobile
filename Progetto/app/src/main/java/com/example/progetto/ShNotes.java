package com.example.progetto;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class ShNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_notes);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        ShNotes.ButtonHandler bh = new ShNotes.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            v.startAnimation(buttonClick);
            finish();
        }
    }
}
