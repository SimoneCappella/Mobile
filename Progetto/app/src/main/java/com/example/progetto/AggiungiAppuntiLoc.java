package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AggiungiAppuntiLoc extends AppCompatActivity implements View.OnClickListener{

    DataAppLoc da;

    EditText editTitolo, editData, editApp;
    Button btnSalva;

    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi__appunti__loc);

        //getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        Intent r = getIntent();
        a = r.getStringExtra("app");

        da = new DataAppLoc(this);

        editTitolo = findViewById(R.id.editTitolo);
        editData = findViewById(R.id.editData);
        editApp = findViewById(R.id.editApp);
        btnSalva = findViewById(R.id.btnSalvaApp);
        btnSalva.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSalvaApp:
                String titolo = editTitolo.getText().toString();
                String data = editData.getText().toString();
                String appunti = editApp.getText().toString();
                da.insert(a, data, titolo, appunti);
                //set Result per passare il risultato alla prima activitu cosi riesco a riavviarla dalla on result
                String res = "appunti salvati";
                Intent intent = new Intent();
                intent = intent.putExtra("res", res);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}
