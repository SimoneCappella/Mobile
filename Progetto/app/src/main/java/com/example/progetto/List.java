package com.example.progetto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class List extends AppCompatActivity implements View.OnClickListener{

    ListView listMaterie;
    TextView textView, textTitolo;
    String[] listItem;
    String value;
    String aula, materia;

    Dialog myDialog;
    EditText editAula, editMateria;

    ImageButton back;
    Button btnAggiungi, btnSvuota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String giorno_ora = intent.getStringExtra("giorno_ora");
        //Toast.makeText(getApplicationContext(), " " + giorno_ora, Toast.LENGTH_SHORT).show();

        listMaterie = findViewById(R.id.listMaterie);
        textView = findViewById(R.id.textView);
        textTitolo = findViewById(R.id.textTitolo);
        textTitolo.setText(giorno_ora);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        btnAggiungi = findViewById(R.id.btnAggiungi);
        btnAggiungi.setOnClickListener(this);
        btnSvuota = findViewById(R.id.btnSvuota);
        btnSvuota.setOnClickListener(this);

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

    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.5F);

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                String[] back = {"back", "back"};

                Intent intentBack = new Intent();
                intentBack = intentBack.putExtra("mat", back);
                setResult(Activity.RESULT_OK, intentBack);
                finish();
                break;
            case R.id.btnAggiungi:
                v.startAnimation(buttonClick);
                Toast.makeText(getApplicationContext(), "Aggiungi una materia", Toast.LENGTH_SHORT).show();
                myDialog.setContentView(R.layout.pop_materia_pers);
                Button btnSalva;
                editAula = myDialog.findViewById(R.id.editAula);
                editMateria = myDialog.findViewById(R.id.editMateria);
                btnSalva = myDialog.findViewById(R.id.btnSalva);
                btnSalva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materia = editMateria.getText().toString();
                        aula = editAula.getText().toString();
                        Toast.makeText(getApplicationContext(), materia + "  " + aula, Toast.LENGTH_SHORT).show();
                        String[] valore = {materia, aula};

                        Intent intent = new Intent();
                        intent = intent.putExtra("mat", valore);
                        setResult(Activity.RESULT_OK, intent);

                        finish();

                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;
            case R.id.btnSvuota:
                v.startAnimation(buttonClick);
                Toast.makeText(getApplicationContext(), "Svuota il campo", Toast.LENGTH_SHORT).show();

                Log.i("value", "2");
                String[] valore = {"svuota", null};
                Intent intent = new Intent();
                intent = intent.putExtra("mat", valore);
                setResult(Activity.RESULT_OK, intent);
                finish();

                break;
        }
    }
}
