package com.example.progetto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class VediAppunti extends AppCompatActivity implements View.OnClickListener{

    TextView txtTitolo, txtData, txtAppunti, txtMateria;
    String titolo, data, app, materia;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedi_appunti);

        //getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        Intent r = getIntent();
        Bundle bundle = r.getBundleExtra("data");

        materia = bundle.getString("materia");
        titolo = bundle.getString("titolo");
        data = bundle.getString("data");
        app = bundle.getString("app");

        txtTitolo = findViewById(R.id.textTitolo);
        txtData = findViewById(R.id.textData);
        txtAppunti = findViewById(R.id.textAppunti);
        txtMateria = findViewById(R.id.textMateria);
        back = findViewById(R.id.btnBack);

        txtMateria.setText(materia);
        txtTitolo.setText(titolo);
        txtData.setText(data);
        txtAppunti.setText(app);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBack:

                finish();
        }
    }
}
