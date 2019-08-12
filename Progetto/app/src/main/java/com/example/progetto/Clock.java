package com.example.progetto;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import java.util.Calendar;

public class Clock extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public int curr = 0;
    public int next;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        Clock.ButtonHandler bh = new Clock.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);
        nav = findViewById(R.id.navigation);

        nav.setOnNavigationItemSelectedListener(this);//settato il listner sulla navigation bar
        nav.setItemIconTintList(null);
        nav.setItemIconSize(110);


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Fragment fragment = null;
        switch (day){
            case Calendar.MONDAY:
                fragment = new lun_fragment();
                nav.setSelectedItemId(R.id.lun);
                break;
            case Calendar.TUESDAY:
                fragment = new mar_fragment();
                nav.setSelectedItemId(R.id.mar);
                break;
            case Calendar.WEDNESDAY:
                fragment = new mer_fragment();
                nav.setSelectedItemId(R.id.mer);
                break;
            case Calendar.THURSDAY:
                fragment = new gio_fragment();
                nav.setSelectedItemId(R.id.gio);
                break;
            case Calendar.FRIDAY:
                fragment = new ven_fragment();
                nav.setSelectedItemId(R.id.ven);
                break;
            case Calendar.SATURDAY:
                fragment = new ven_fragment();
                nav.setSelectedItemId(R.id.ven);
                break;
            case Calendar.SUNDAY:
                fragment = new lun_fragment();
                nav.setSelectedItemId(R.id.lun);
                break;
        }
        loadFragment(fragment);
    }



    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    private boolean loadFragment(android.support.v4.app.Fragment fragment)    //per caricare il fragment
    {
        if(fragment!= null)
            {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(curr < next)
                {
                    ft.setCustomAnimations(R.anim.slide_in,R.anim.slide_out);
                }else
                    {
                        ft.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                    }
            curr = next;
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();                                                                                         //tramite il frag manag e la transaction viene rimpiazzato il
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
                next = 0;
                fragment = new lun_fragment();
                break;
            case R.id.mar:
                next = 1;
                fragment = new mar_fragment();
                break;
            case R.id.mer:
                next = 2;
                fragment = new mer_fragment();
                break;
            case R.id.gio:
                next = 3;
                fragment = new gio_fragment();
                break;
            case R.id.ven:
                next = 4;
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
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }
}
