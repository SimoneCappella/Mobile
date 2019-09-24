package com.example.progetto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * Activity che viene lanciata da un fragment relativo ad un giorno dall'orario, questa classe carica la lista delle materie da cui poterne scegliere una,
 * Successivamente lancia il PopUp dal quale è possibile indicare aula e numero di ore da salvare per la materia scelta
 */
public class List extends AppCompatActivity implements View.OnClickListener{

    ListView listMaterie;
    TextView textView, textTitolo, textContatore;
    String[] listItem;
    String value;
    String aula, materia, conta;

    Dialog myDialog; //Popup
    EditText editAula, editMateria;

    ImageButton back, btnMeno, btnPiu;
    Button btnAggiungi;
    int i;

    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.5F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String giorno_ora = intent.getStringExtra("giorno_ora");
        i = 1;

        //Dichiarazione e caricamento della lista delle materie e relativi elementi
        listMaterie = findViewById(R.id.listMaterie);
        textView = findViewById(R.id.textView);
        textTitolo = findViewById(R.id.textTitolo);
        textTitolo.setText(giorno_ora);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        btnAggiungi = findViewById(R.id.btnAggiungi);
        btnAggiungi.setOnClickListener(this);
        listItem = getResources().getStringArray(R.array.Materie);

        //viene istanziato il PopUp, come oggetto della classe Dialog.
        myDialog = new Dialog(this);

        final ArrayAdapter<String>  adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);

        listMaterie.setAdapter(adapter);
        //listner sulla lista delle materie
        listMaterie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                value = adapter.getItem(position); //prende il valore della materia scelta grazie alla posizione nella lista
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show(); //genera un toast con la materia scelta

                //Lancia il PopUp, gestisce l'inserimento dell'aula, la conta delle ore e successivamente il salvataggio e l'impacchettamento per restituire i dati all'activity chiamante.
                myDialog.setContentView(R.layout.pop_aula);
                Button btnSalva;
                myDialog.setContentView(R.layout.pop_aula);
                editAula = myDialog.findViewById(R.id.editAula);
                textContatore = myDialog.findViewById(R.id.textContatore);
                textContatore.setText(Integer.toString(i));
                btnMeno = myDialog.findViewById(R.id.btnMeno);
                btnMeno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i--;
                        textContatore.setText(Integer.toString(i));
                    }
                });
                btnPiu = myDialog.findViewById(R.id.btnPiu);
                btnPiu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i++;
                        textContatore.setText(Integer.toString(i));
                    }
                });
                btnSalva = myDialog.findViewById(R.id.btnSalva);
                btnSalva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aula = editAula.getText().toString();
                        conta = textContatore.getText().toString();
                        Toast.makeText(getApplicationContext(), "salvato", Toast.LENGTH_SHORT).show();
                        String[] valore = {value, aula, conta};

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

    /**
     * Gestisce la selezione del bottone back o la scelta per l'aggiunta di una nuova materia; in tal caso si lancia sempre un PopUp ma nel quale è chiesto di inserire anche la materia.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                String[] back = {"back", "back", "0"};

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
                textContatore = myDialog.findViewById(R.id.textContatore);
                textContatore.setText(Integer.toString(i));
                btnMeno = myDialog.findViewById(R.id.btnMeno);
                btnMeno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i--;
                        textContatore.setText(Integer.toString(i));
                    }
                });
                btnPiu = myDialog.findViewById(R.id.btnPiu);
                btnPiu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i++;
                        textContatore.setText(Integer.toString(i));
                    }
                });
                btnSalva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materia = editMateria.getText().toString();
                        aula = editAula.getText().toString();
                        conta = textContatore.getText().toString();
                        String[] valore = {materia, aula, conta};

                        Intent intent = new Intent();
                        intent = intent.putExtra("mat", valore);
                        setResult(Activity.RESULT_OK, intent);

                        finish();

                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;
        }
    }
}
