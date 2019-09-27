package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Activity con la lista degli appunti salvati in locale
 */
public class MyNotes extends AppCompatActivity implements View.OnClickListener{

    ListView listMat;
    DataManager dm;

    ImageButton back;
    Button btnAgg;

    SimpleCursorAdapter adapter;
    Cursor cursor;

    public static final int REQUEST_CODE2 = 2222;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        listMat = findViewById(R.id.listMatLoc);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        btnAgg = findViewById(R.id.btnAgg);
        btnAgg.setOnClickListener(this);

        dm = new DataManager(this);
        cursor = dm.selectAll();

        String[] fromColumns={"m"};
        int[] viewsList = {R.id.txtMat};

        //Il cursore riempie la lista con le materie prese dal database delle materie
        adapter = new SimpleCursorAdapter(this, R.layout.list_mat, cursor, fromColumns, viewsList, 0);

        listMat.setAdapter(adapter);

        //Gestisce la scelta su un elemento dalla lista degli appunti
        listMat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = adapter.getCursor();
                String a = c.getString(2);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplication(), Notes_Page.class);
                i.putExtra("mat", a);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btnAgg:
                v.startAnimation(buttonClick);
                launchAggMat();
                break;
        }
    }

    private void launchAggMat() {
        Intent intent = new Intent(this, AggMaterie.class);
        intent.putExtra("app", "agg materie");
        startActivityForResult(intent, REQUEST_CODE2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE2) && (resultCode == Activity.RESULT_OK)) {
            String res = data.getStringExtra("res");
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
        Cursor nc;
        nc = dm.selectAll();
        adapter.changeCursor(nc);           //Utilizzati per aggiornare la lista
        adapter.notifyDataSetChanged();     //una volta inserito una nuova materia
    }

}
