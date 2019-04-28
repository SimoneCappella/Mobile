package com.example.progetto;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

public class AggMaterie extends AppCompatActivity{

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    /*Button btnSalva;
    Button btnCerca;
    Button btnCancella;
    Button btnAll;*/

    EditText editMateria;
    EditText editOrario;
    EditText editCancella;
    EditText editCerca;

    public DataManager dm; //DataManager è la classe con i metodi per gestire il db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_materie);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        AggMaterie.ButtonHandler bh = new AggMaterie.ButtonHandler();

        findViewById(R.id.btnBack).setOnClickListener(bh);
        findViewById(R.id.btnSalva).setOnClickListener(bh);
        findViewById(R.id.btnCancella).setOnClickListener(bh);
        findViewById(R.id.btnCerca).setOnClickListener(bh);
        findViewById(R.id.btnAll).setOnClickListener(bh);

        /*btnSalva = (Button) findViewById(R.id.btnSalva);
        btnCerca = (Button) findViewById(R.id.btnCerca);
        btnCancella = (Button) findViewById(R.id.btnCancella);
        btnAll = (Button) findViewById(R.id.btnAll);*/

        EditText editMateria = findViewById(R.id.editMateria);
        EditText editOrario = findViewById(R.id.editOrario);
        EditText editCerca = findViewById(R.id.editCerca);
        EditText editCancella = findViewById(R.id.editCancella);

        /*editMateria = (EditText) findViewById(R.id.editMateria);
        editOrario = (EditText) findViewById(R.id.editOrario);
        editCerca = (EditText) findViewById(R.id.editCerca);
        editCancella = (EditText) findViewById(R.id.editCancella);*/

        /*btnSalva.setOnClickListener((View.OnClickListener) this);
        btnCerca.setOnClickListener((View.OnClickListener) this);
        btnCancella.setOnClickListener((View.OnClickListener) this);
        btnAll.setOnClickListener((View.OnClickListener) this);*/

        dm = new DataManager(this);

    }

    public void showData (Cursor c){
        while(c.moveToNext()){
            Log.i(c.getString(1), c.getString(2));
        }
    }

    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBack:
                    v.startAnimation(buttonClick);
                    finish();
                    break;
                case R.id.btnSalva:
                    v.startAnimation(buttonClick);
                    launchSave(v);
                    break;
                case R.id.btnCancella:
                    dm.delete(editCancella.getText().toString());
                    break;
                case R.id.btnCerca:
                    showData(dm.search(editCerca.getText().toString()));
                    break;
                case R.id.btnAll:
                    showData(dm.selectAll());
                    break;
            }
        }
    }

    public void launchSave (View v){
        dm.insert(editMateria.getText().toString(), editOrario.getText().toString());
    }
}
