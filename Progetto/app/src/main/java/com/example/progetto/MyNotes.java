package com.example.progetto;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MyNotes extends AppCompatActivity implements View.OnClickListener{

    ListView listMat;

    ImageButton back;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));

        back = findViewById(R.id.btnBack);
        back.setOnClickListener(this);

        DataManager dm = new DataManager(this);
        final Cursor cursor = dm.selectAll();

        String[] fromColumns={"m"};
        int[] viewsList = {R.id.txtMat};

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_mat, cursor, fromColumns, viewsList, 0);

        listMat = findViewById(R.id.listMatLoc);
        listMat.setAdapter(adapter);

        listMat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c = adapter.getCursor();
                String a = c.getString(2);
                Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplication(), Notes_Page.class);
                i.putExtra("mat", a);
                startActivity(i);
            }
        });
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                v.startAnimation(buttonClick);
                finish();
        }
    }
}
