package com.example.progetto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    ListView lv;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        lv = findViewById(R.id.listview);


        final ArrayList<String> al = new ArrayList<>();

        al.add("Modifica Username");
        al.add("Modifica Password");
        al.add("About Us");

        ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(arrayadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        launchEditUser(view);
                        break;
                    case 1:
                        launchEditPass(view);
                        break;
                    case 2:
                        launchAboutUs(view);
                        break;
                }
            }
        });
    }

    public void launchAboutUs(View v)
    {
        Intent i = new Intent(this , AboutUs.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void launchEditUser(View v)
    {
        Intent i = new Intent(this , EditUser.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void launchEditPass(View v)
    {
        Intent i = new Intent(this , editPass.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }
}
