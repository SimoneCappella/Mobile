package com.example.progetto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class editPass extends AppCompatActivity implements View.OnClickListener{

    TextView txtOldPass;
    TextView txtNewPass;
    Button btnChangePass;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        txtOldPass = findViewById(R.id.txtOldPass);
        txtNewPass = findViewById(R.id.txtNewPass);
        btnChangePass = findViewById(R.id.btnEditPass);
        back = findViewById(R.id.back);
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
