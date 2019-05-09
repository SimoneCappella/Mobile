package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Notes_Page extends AppCompatActivity implements View.OnClickListener {

    TextView txt;

    ListView listNote;

    ImageButton back;

    Button btnAggiungiApp;

    String a;

    Cursor cursor;

    SimpleCursorAdapter adapter;

    DataAppLoc da;

    public static final int REQUEST_CODE1 = 1111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_page);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        Intent r = getIntent();
        a = r.getStringExtra("mat");

        txt = findViewById(R.id.textMateria);
        txt.setText(a);

        back = findViewById(R.id.btnBack);
        btnAggiungiApp = findViewById(R.id.btnAggiungi);
        back.setOnClickListener(this);
        btnAggiungiApp.setOnClickListener(this);

        da = new DataAppLoc(this);
        cursor = da.searchM(a);

        String[] fromColumns={"t", "d"};
        int[] viewsList = {R.id.txtTitle, R.id.txtDate};

        adapter = new SimpleCursorAdapter(this, R.layout.list_app_loc, cursor, fromColumns, viewsList, 0);

        listNote = findViewById(R.id.listApp);
        listNote.setAdapter(adapter);

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = adapter.getCursor();
                String b = c.getString(1);
                String titolo = c.getString(3);
                String data = c.getString(2);
                String app = c.getString(4);
                Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();

                launchVedi(b, titolo, data, app);

                /*Intent i = new Intent(getApplication(), VediAppunti.class);
                Bundle bundle = new Bundle();
                bundle.putString("materia", b);
                bundle.putString("titolo", titolo);
                bundle.putString("data", data);
                bundle.putString("app", app);

                i.putExtra("data", bundle);

                startActivity(i);*/
            }
        });
    }

    /*@Override
    protected void onStart(){

        super.onStart();

        final DataAppLoc da = new DataAppLoc(this);
        Cursor cursor = da.searchM(a);

        String[] fromColumns={"t", "d"};
        int[] viewsList = {R.id.txtTitle, R.id.txtDate};

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_app_loc, cursor, fromColumns, viewsList, 0);

        listNote = findViewById(R.id.listApp);
        listNote.setAdapter(adapter);

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = adapter.getCursor();
                String b = c.getString(1);
                String titolo = c.getString(3);
                String data = c.getString(2);
                String app = c.getString(4);
                Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();

                launchVedi(b, titolo, data, app);

                Intent i = new Intent(getApplication(), VediAppunti.class);
                Bundle bundle = new Bundle();
                bundle.putString("materia", b);
                bundle.putString("titolo", titolo);
                bundle.putString("data", data);
                bundle.putString("app", app);
                i.putExtra("data", bundle);
                startActivity(i);

            }
        });
    }*/

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                v.startAnimation(buttonClick);
                finish();
                break;
            case R.id.btnAggiungi:
                v.startAnimation(buttonClick);
                launchAggiungi();
                break;
        }
    }

    public void launchAggiungi(){
        Intent intent = new Intent(this, AggiungiAppuntiLoc.class);
        intent.putExtra("app", a);
        startActivityForResult(intent, REQUEST_CODE1);
    }

    public void launchVedi(String b, String titolo, String data, String app){
        Intent i = new Intent(getApplication(), VediAppunti.class);
        Bundle bundle = new Bundle();
        bundle.putString("materia", b);
        bundle.putString("titolo", titolo);
        bundle.putString("data", data);
        bundle.putString("app", app);
        i.putExtra("data", bundle);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE1) && (resultCode == Activity.RESULT_OK)) {
            String res = data.getStringExtra("res");
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
        startActivity(getIntent());
        finish();
        overridePendingTransition(0, 0);
    }
}
