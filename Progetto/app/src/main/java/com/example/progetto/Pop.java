package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pop extends AppCompatActivity implements View.OnClickListener {

    Button btnSalva;

    EditText editAula;

    String value;

    lun_fragment aula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        editAula = findViewById(R.id.editAula);

        btnSalva = findViewById(R.id.btnSalva);
        btnSalva.setOnClickListener(this);

        aula = new lun_fragment();
    }

    public void assegna(String a){
        a = value;
    }


    @Override
    public void onClick(View v) {
        value = editAula.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("aula", value);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
