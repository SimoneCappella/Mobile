package com.example.progetto;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import java.util.Calendar;

/**
 * Classe principale dell'orario, lancia il fragment relativo al giorno corrente
 */
public class Clock extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private int curr = 0;
    private int next;
    private BottomNavigationView nav;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        /**
         * Blocco utilizzato per impostare la larghezza e l'altezza del fragment contenente l'orario, allo stesso modo per la BottomNavigationView
         */
        MainActivity misure = new MainActivity();
        int height = misure.getHeight();
        int width = misure.getWidth();
        int navHeight = (height*5)/100;
        height = (height*90)/100;

        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, height);
        frameLayout.setLayoutParams(params);

        Clock.ButtonHandler bh = new Clock.ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);
        nav = findViewById(R.id.navigation);

        //dimensione della bottom navbar
        navHeight = navHeight*70/100;

        //settato il listner sulla navigation bar
        nav.setOnNavigationItemSelectedListener(this);
        nav.setItemIconTintList(null);
        nav.setItemIconSize(navHeight);

        /**
         * Switch sul giorno corrente per caricare il relativo fragment.
         */
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

    /**
     * Funzione per caricare il fragment, con le variabili curr e next gestisce l'animazione a scorrimento.
     * @param fragment fragment da caricare.
     * @return
     */
    private boolean loadFragment(android.support.v4.app.Fragment fragment)
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
            ft.commit();
            return true;
        }
        return false;
    }

    /**
     * Funzione utilizzata nel momento in cui si sceglie un elemento dalla BottomNavigationBar, lo switch prepara il fragment da passare alla funzione load fragment e imposta la variabile next.
     * @param item item scelto dalla BottomNavigationBar.
     * @return
     */
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
        return loadFragment(fragment);
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
