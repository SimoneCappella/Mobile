package com.example.progetto;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AggMaterie extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack;

    Button btnInsert;
    Button btnDelete;
    Button btnSelect;
    Button btnSearch;

    EditText editMateria;
    EditText editOrario;
    EditText editDelete;
    EditText editSearch;

    private DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_materie);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        dm = new DataManager(this);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        editMateria = (EditText) findViewById(R.id.editMateria);
        editOrario = (EditText) findViewById(R.id.editOrario);
        editDelete = (EditText) findViewById(R.id.editDelete);
        editSearch = (EditText) findViewById(R.id.editSearch);

        btnBack.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    public void showData (Cursor c){
        while(c.moveToNext()){
            String query = " / code: " + c.getString(1) + " / materia: " + c.getString(2) + "/ ora: " + c.getString(3) + "/ aula: " + c.getString(4);
            Log.i("Data ---->", query);
            /*String queryC = "c:" + c.getString(2);
            Log.i("data", queryC);*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                v.startAnimation(buttonClick);
                finish();
                break;
            case R.id.btnInsert:
                v.startAnimation(buttonClick);
                dm.insert(editMateria.getText().toString(), editOrario.getText().toString(), "aula", "cdoe");
                break;
            case R.id.btnSelect:
                v.startAnimation(buttonClick);
                showData(dm.selectAll());
                break;
            case R.id.btnSearch:
                v.startAnimation(buttonClick);
                showData(dm.searchByCode(editSearch.getText().toString()));
                break;
            case R.id.btnDelete:
                v.startAnimation(buttonClick);
                dm.delete(editDelete.getText().toString());
                break;
        }
    }
}

