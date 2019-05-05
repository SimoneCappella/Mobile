package com.example.progetto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class List extends AppCompatActivity implements View.OnClickListener{

    //ImageButton back;

    ListView listMaterie;
    TextView textView;
    String[] listItem;
    String a;
    String value;
    String aula;

    public static final int REQUEST_CODE1 = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //back = findViewById(R.id.back);
        listMaterie = findViewById(R.id.listappunticonivisi);
        textView = findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.Materie);

        final ArrayAdapter<String>  adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listMaterie.setAdapter(adapter);

        //back.setOnClickListener(this);

        listMaterie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent pop = new Intent(getApplication(), Pop.class);
                startActivityForResult(pop, REQUEST_CODE1);

                value = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE1) && (resultCode == Activity.RESULT_OK)) {
            aula = data.getStringExtra("aula");

            Toast.makeText(getApplicationContext(), "aula: " + aula, Toast.LENGTH_SHORT).show();
            String[] valore = {value, aula};
            Intent intent = new Intent();
            Intent intent1 = intent.putExtra("mat", valore);
            setResult(Activity.RESULT_OK, intent1);
        }
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}
