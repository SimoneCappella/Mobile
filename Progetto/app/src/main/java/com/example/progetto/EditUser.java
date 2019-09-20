package com.example.progetto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    TextView txtNewusr;
    Button btnEdit;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        back = findViewById(R.id.back);
        txtNewusr = findViewById(R.id.txtNewPass);
        btnEdit = findViewById(R.id.btnEditPass);
        back.setOnClickListener(this);

    }
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

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
