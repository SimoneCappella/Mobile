package com.example.progetto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class List extends AppCompatActivity implements View.OnClickListener{

    ListView listMaterie;
    TextView textView;
    String[] listItem;
    String value;
    String aula;

    Dialog myDialog;
    EditText editAula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listMaterie = findViewById(R.id.listMaterie);
        textView = findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.Materie);

        myDialog = new Dialog(this);

        final ArrayAdapter<String>  adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listMaterie.setAdapter(adapter);

        listMaterie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                value = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                myDialog.setContentView(R.layout.pop_aula);
                Button btnSalva;
                myDialog.setContentView(R.layout.pop_aula);
                editAula = myDialog.findViewById(R.id.editAula);
                btnSalva = myDialog.findViewById(R.id.btnSalva);
                btnSalva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aula = editAula.getText().toString();
                        Toast.makeText(getApplicationContext(), "aula: " + aula, Toast.LENGTH_SHORT).show();
                        String[] valore = {value, aula};

                        Intent intent = new Intent();
                        intent = intent.putExtra("mat", valore);
                        setResult(Activity.RESULT_OK, intent);

                        finish();

                        myDialog.dismiss();
                    }
                });
                myDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}
