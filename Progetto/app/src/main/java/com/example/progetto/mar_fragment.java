package com.example.progetto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class mar_fragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v1 = inflater.inflate(R.layout.mar, container, false);

        TextView txtMat1 = v1.findViewById(R.id.materia1);
        txtMat1.setText("che");
        TextView txtMat2 = v1.findViewById(R.id.materia2);
        TextView txtMat3 = v1.findViewById(R.id.materia3);
        TextView txtMat4 = v1.findViewById(R.id.materia4);
        TextView txtMat5 = v1.findViewById(R.id.materia5);
        TextView txtMat6 = v1.findViewById(R.id.materia6);
        TextView txtMat7 = v1.findViewById(R.id.materia7);
        TextView txtMat8 = v1.findViewById(R.id.materia8);
        TextView txtMat9 = v1.findViewById(R.id.materia9);
        TextView txtMat10 = v1.findViewById(R.id.materia10);
        TextView txtMat11 = v1.findViewById(R.id.materia11);

        ImageView btnEdit1 = v1.findViewById(R.id.edit1);
        btnEdit1.setOnClickListener(this);
        ImageView btnEdit2 = v1.findViewById(R.id.edit2);
        btnEdit2.setOnClickListener(this);
        ImageView btnEdit3 = v1.findViewById(R.id.edit3);
        btnEdit3.setOnClickListener(this);
        ImageView btnEdit4 = v1.findViewById(R.id.edit4);
        btnEdit4.setOnClickListener(this);
        ImageView btnEdit5 = v1.findViewById(R.id.edit5);
        btnEdit5.setOnClickListener(this);
        ImageView btnEdit6 = v1.findViewById(R.id.edit6);
        btnEdit6.setOnClickListener(this);
        ImageView btnEdit7 = v1.findViewById(R.id.edit7);
        btnEdit7.setOnClickListener(this);
        ImageView btnEdit8 = v1.findViewById(R.id.edit8);
        btnEdit8.setOnClickListener(this);
        ImageView btnEdit9 = v1.findViewById(R.id.edit9);
        btnEdit9.setOnClickListener(this);
        ImageView btnEdit10 = v1.findViewById(R.id.edit10);
        btnEdit10.setOnClickListener(this);
        ImageView btnEdit11 = v1.findViewById(R.id.edit11);
        btnEdit11.setOnClickListener(this);

        return v1;
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.5F);

    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.edit1:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit2:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit3:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit4:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit5:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit6:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit7:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit8:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit9:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit10:
                v.startAnimation(buttonClick);
                break;
            case R.id.edit11:
                v.startAnimation(buttonClick);
                break;
        }
    }
}
