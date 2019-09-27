package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AggMaterie extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack;

    Button btnInsert;
    Button btnDelete;

    EditText editMateria;
    //EditText editDelete;

    String materia;
    private DataManager dm;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_materie);

        dm = new DataManager(this);

        btnBack = (ImageButton) findViewById(R.id.back);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        editMateria = (EditText) findViewById(R.id.editMateria);
        //editDelete = (EditText) findViewById(R.id.editDelete);

        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnInsert.setOnClickListener(this);

        Intent intent = getIntent();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                break;
            case R.id.btnInsert:
                v.startAnimation(buttonClick);
                materia = editMateria.getText().toString();
                Cursor c = dm.searchM(materia);
                if(c.getCount() > 0){
                    Toast.makeText(getApplicationContext(), materia + " esiste già", Toast.LENGTH_SHORT).show();
                }else{
                    dm.insert(materia, "ora", "aula", "code");
                    Intent res  = new Intent();
                    res = res.putExtra("res", materia);
                    setResult(Activity.RESULT_OK, res);
                    finish();
                }
                break;
            case R.id.btnDelete:
                v.startAnimation(buttonClick);
                String materia = editMateria.getText().toString();
                Cursor del = dm.searchM(materia);
                if (del.getCount() > 0){
                    Toast.makeText(getApplicationContext(), materia + " eliminata con successo", Toast.LENGTH_SHORT).show();
                    dm.delete(materia);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "la materia non esiste", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

