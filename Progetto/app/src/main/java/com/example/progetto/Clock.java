package com.example.progetto;

import android.app.Fragment;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;

public class Clock extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#cccccc"));
        Clock.ButtonHandler bh = new Clock.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);
        BottomNavigationView nav = findViewById(R.id.navigation);
        nav.setItemIconTintList(null);
        nav.setItemIconSize(110);
        nav.setOnNavigationItemSelectedListener(this);//settato il listner sulla navigation bar



        loadFragment(new lun_fragment());  //il fragment di lunedi di default
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private boolean loadFragment(android.app.Fragment fragment)    //per caricare il fragment
    {
        if(fragment!= null)
        {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();         //tramite il frag manag e la transaction viene rimpiazzato il
            return true;                                                                                         //fragment container (che era il nome del frame dato allo spazio
        }                                                                                                        //dove appaiono i fragment) con il fragment che viene passato al metodo
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  //semplice switch che associa in base all' item menu cliccato  un'istanza del fragment all oggetto fragment
        Fragment fragment = null;
        switch(item.getItemId())
        {
            case R.id.lun:
                fragment = new lun_fragment();
                break;
            case R.id.mar:
                fragment = new mar_fragment();
                break;
            case R.id.mer:
                fragment = new mer_fragment();
                break;
            case R.id.gio:
                fragment = new gio_fragment();
                break;
            case R.id.ven:
                fragment = new ven_fragment();
                break;
        }
        return loadFragment(fragment);       //viene passato al metodo load fragment il fragment selezionato dallo switch
    }

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
