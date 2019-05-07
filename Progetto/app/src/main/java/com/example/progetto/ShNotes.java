package com.example.progetto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShNotes extends AppCompatActivity {

    ListView ListaAppunti;
    final Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_notes);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        ShNotes.ButtonHandler bh = new ShNotes.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);

        //CURSOR FETCHALL
        DataManager mydatabase=new DataManager(ctx);

        String[] fromColumns={"m","o","a"};
        int[] toViews = {R.id.nomeMateria,R.id.orario,R.id.aula};

        final Cursor cursor = mydatabase.selectAll();
        final SimpleCursorAdapter arrayAdapter = new SimpleCursorAdapter(this,R.layout.model,cursor,fromColumns,toViews,0);

//        final ArrayAdapter<String> arrayAdapter;
//        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ListaLuoghi);

        ListaAppunti = findViewById(R.id.listMaterie);
        ListaAppunti.setAdapter(arrayAdapter);


        ListaAppunti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView testoLat = view.findViewById(R.id.orario);
                TextView testoLng = view.findViewById(R.id.aula);

                Intent returnIntent =new Intent();
                returnIntent.putExtra("LATITUDINE",testoLat.getText());
                returnIntent.putExtra("LONGITUDINE",testoLng.getText());
                setResult(2,returnIntent );

                finish();//finishing activity

            }
        });



        ListaAppunti.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                //deve comparire conferma poi cancello l'elemento


                LayoutInflater li=LayoutInflater.from(ctx);
                View propmtsView=li.inflate(R.layout.messaggio,null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(ctx);
                alertDialogBuilder.setView(propmtsView);




                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView nomeLuogo = view.findViewById(R.id.nomeMateria);
                        DataManager mydatabase=new DataManager(ctx);
                        mydatabase.cancellaLuogo ( ctx, (String) nomeLuogo.getText());

                        cursor.requery();
                        arrayAdapter.notifyDataSetChanged();

//                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });

                alertDialogBuilder.setCancelable(false);
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });










    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            v.startAnimation(buttonClick);
            finish();
        }
    }


}
