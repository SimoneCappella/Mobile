package com.example.progetto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class lun_fragment extends Fragment implements View.OnClickListener {

    private DataManager dm;

    public static final int REQUEST_CODE = 0000;

    SalvaOrario sa;

    TextView txtMat1, txtMat2, txtMat3, txtMat4, txtMat5, txtMat6, txtMat7, txtMat8, txtMat9, txtMat10, txtMat11, txtMat0;
    TextView txtOra1, txtOra2, txtOra3, txtOra4, txtOra5, txtOra6, txtOra7, txtOra8, txtOra9, txtOra10, txtOra11, txtOra0;
    TextView txtAula1, txtAula2, txtAula3, txtAula4, txtAula5, txtAula6, txtAula7, txtAula8, txtAula9, txtAula10, txtAula11, txtAula0;
    TextView txtDel0;

    String ora, materia, aula, i="lun_";
    int n, inc;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.lun, container, false);

        MainActivity misure = new MainActivity();
        int height = misure.getHeight();
        int widht = misure.getWidth();
        Log.i("larghezza", "la larghezza è:" + widht + " e l'altezza è: " + height);

        int widthMat = (widht*50)/100;
        int widthOra = (widht*15)/100;
        int widthAula = (widht*25)/100;
        int widthElimina = (widht*10)/100;
        height = (height*50)/100;
        Log.i("larghezza", "l'altezza è:" + height);
        height = height/13;
        Log.i("larghezza", "l'altezza divisa è:" + height);

        TableRow.LayoutParams materia = new TableRow.LayoutParams(widthMat, height);
        TableRow.LayoutParams ora = new TableRow.LayoutParams(widthOra, height);
        TableRow.LayoutParams aula = new TableRow.LayoutParams(widthAula, height);
        TableRow.LayoutParams elimina = new TableRow.LayoutParams(widthElimina, height);

        txtMat0 = v.findViewById(R.id.materia);
        txtOra0 = v.findViewById(R.id.ora);
        txtAula0 = v.findViewById(R.id.aula);
        txtDel0 = v.findViewById(R.id.elimina);

        txtMat0.setLayoutParams(materia);
        txtOra0.setLayoutParams(ora);
        txtAula0.setLayoutParams(aula);
        txtDel0.setLayoutParams(elimina);

        txtMat1 = v.findViewById(R.id.materia1);
        txtMat1.setText(sa.getMateria("lun_1", getActivity()));
        txtMat1.setOnClickListener(this);
        txtAula1 = v.findViewById(R.id.aula1);
        txtAula1.setText(sa.getAula("lun_1", getActivity()));
        txtOra1 = v.findViewById(R.id.ora1);

        txtMat2 = v.findViewById(R.id.materia2);
        txtMat2.setText(sa.getMateria("lun_2", getActivity()));
        txtMat2.setOnClickListener(this);
        txtAula2 = v.findViewById(R.id.aula2);
        txtAula2.setText(sa.getAula("lun_2", getActivity()));
        txtOra2 = v.findViewById(R.id.ora2);

        txtMat3 = v.findViewById(R.id.materia3);
        txtMat3.setText(sa.getMateria("lun_3", getActivity()));
        txtMat3.setOnClickListener(this);
        txtAula3 = v.findViewById(R.id.aula3);
        txtAula3.setText(sa.getAula("lun_3", getActivity()));
        txtOra3 = v.findViewById(R.id.ora3);

        txtMat4 = v.findViewById(R.id.materia4);
        txtMat4.setText(sa.getMateria("lun_4", getActivity()));
        txtMat4.setOnClickListener(this);
        txtAula4 = v.findViewById(R.id.aula4);
        txtAula4.setText(sa.getAula("lun_4", getActivity()));
        txtOra4 = v.findViewById(R.id.ora4);

        txtMat5 = v.findViewById(R.id.materia5);
        txtMat5.setText(sa.getMateria("lun_5", getActivity()));
        txtMat5.setOnClickListener(this);
        txtAula5 = v.findViewById(R.id.aula5);
        txtAula5.setText(sa.getAula("lun_5", getActivity()));
        txtOra5 = v.findViewById(R.id.ora5);

        txtMat6 = v.findViewById(R.id.materia6);
        txtMat6.setText(sa.getMateria("lun_6", getActivity()));
        txtMat6.setOnClickListener(this);
        txtAula6 = v.findViewById(R.id.aula6);
        txtAula6.setText(sa.getAula("lun_6", getActivity()));
        txtOra6 = v.findViewById(R.id.ora6);

        txtMat7 = v.findViewById(R.id.materia7);
        txtMat7.setText(sa.getMateria("lun_7", getActivity()));
        txtMat7.setOnClickListener(this);
        txtAula7 = v.findViewById(R.id.aula7);
        txtAula7.setText(sa.getAula("lun_7", getActivity()));
        txtOra7 = v.findViewById(R.id.ora7);

        txtMat8 = v.findViewById(R.id.materia8);
        txtMat8.setText(sa.getMateria("lun_8", getActivity()));
        txtMat8.setOnClickListener(this);
        txtAula8 = v.findViewById(R.id.aula8);
        txtAula8.setText(sa.getAula("lun_8", getActivity()));
        txtOra8 = v.findViewById(R.id.ora8);

        txtMat9 = v.findViewById(R.id.materia9);
        txtMat9.setText(sa.getMateria("lun_9", getActivity()));
        txtMat9.setOnClickListener(this);
        txtAula9 = v.findViewById(R.id.aula9);
        txtAula9.setText(sa.getAula("lun_9", getActivity()));
        txtOra9 = v.findViewById(R.id.ora9);

        txtMat10 = v.findViewById(R.id.materia10);
        txtMat10.setText(sa.getMateria("lun_10", getActivity()));
        txtMat10.setOnClickListener(this);
        txtAula10 = v.findViewById(R.id.aula10);
        txtAula10.setText(sa.getAula("lun_10", getActivity()));
        txtOra10 = v.findViewById(R.id.ora10);

        txtMat11 = v.findViewById(R.id.materia11);
        txtMat11.setText(sa.getMateria("lun_11", getActivity()));
        txtMat11.setOnClickListener(this);
        txtAula11 = v.findViewById(R.id.aula11);
        txtAula11.setText(sa.getAula("lun_11", getActivity()));
        txtOra11 = v.findViewById(R.id.ora11);

        ImageView btnEdit1 = v.findViewById(R.id.edit1);
        btnEdit1.setOnClickListener(this);
        ImageView btnEdit2 = v.findViewById(R.id.edit2);
        btnEdit2.setOnClickListener(this);
        ImageView btnEdit3 = v.findViewById(R.id.edit3);
        btnEdit3.setOnClickListener(this);
        ImageView btnEdit4 = v.findViewById(R.id.edit4);
        btnEdit4.setOnClickListener(this);
        ImageView btnEdit5 = v.findViewById(R.id.edit5);
        btnEdit5.setOnClickListener(this);
        ImageView btnEdit6 = v.findViewById(R.id.edit6);
        btnEdit6.setOnClickListener(this);
        ImageView btnEdit7 = v.findViewById(R.id.edit7);
        btnEdit7.setOnClickListener(this);
        ImageView btnEdit8 = v.findViewById(R.id.edit8);
        btnEdit8.setOnClickListener(this);
        ImageView btnEdit9 = v.findViewById(R.id.edit9);
        btnEdit9.setOnClickListener(this);
        ImageView btnEdit10 = v.findViewById(R.id.edit10);
        btnEdit10.setOnClickListener(this);
        ImageView btnEdit11 = v.findViewById(R.id.edit11);
        btnEdit11.setOnClickListener(this);

        dm = new DataManager(getActivity());
        sa = new SalvaOrario();

        return v;
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.5F);

    public void onClick (View v)
    {
        switch (v.getId())
        {
            case R.id.materia1:
                n = 1;
                ora = txtOra1.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia2:
                n = 2;
                ora = txtOra2.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia3:
                n = 3;
                ora = txtOra3.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia4:
                n = 4;
                ora = txtOra4.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia5:
                n = 5;
                ora = txtOra5.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia6:
                n = 6;
                ora = txtOra6.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia7:
                n = 7;
                ora = txtOra7.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia8:
                n = 8;
                ora = txtOra8.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia9:
                n = 9;
                ora = txtOra9.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia10:
                n = 10;
                ora = txtOra10.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.materia11:
                n = 11;
                ora = txtOra11.getText().toString();
                v.startAnimation(buttonClick);
                launchList();
                break;
            case R.id.edit1:
                n = 1;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit2:
                n = 2;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit3:
                n = 3;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit4:
                n = 4;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit5:
                n = 5;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit6:
                n = 6;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit7:
                n = 7;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit8:
                n = 8;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit9:
                n = 9;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit10:
                n = 10;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
            case R.id.edit11:
                n = 11;
                v.startAnimation(buttonClick);
                materia = null;
                aula = null;
                inc = 1;
                inserisciSalva();
                break;
        }
    }


    public void launchList(){
        Intent intent = new Intent (getActivity(), List.class);
        intent.putExtra("giorno_ora", "lunedì  " + ora);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            String[] res = data.getStringArrayExtra("mat");
            materia = res[0];
            aula = res[1];
            inc = Integer.parseInt(res[2]);
        }
        if (materia.equals("back")){}
        else{
            salvaOrario(i, materia, aula);
        }
    }

    public void salvaOrario (String key, String materia, String aula) {
        if (dm.searchM(materia) != null) {
            dm.delete(materia);
        }
        dm.insert(materia, ora, aula, key); //salva la materia nel db
        inserisciSalva();
    }

    public void inserisciSalva(){
        while (inc > 0){
            String q = i + n;
            sa.setMateria(q, materia, getActivity());
            sa.setAula(q, aula, getActivity());

            switch (n){
                case 1:
                    txtMat1.setText(sa.getMateria(q, getActivity()));
                    txtAula1.setText(sa.getAula(q, getActivity()));
                    break;
                case 2:
                    txtMat2.setText(sa.getMateria(q, getActivity()));
                    txtAula2.setText(sa.getAula(q, getActivity()));
                    break;
                case 3:
                    txtMat3.setText(sa.getMateria(q, getActivity()));
                    txtAula3.setText(sa.getAula(q, getActivity()));
                    break;
                case 4:
                    txtMat4.setText(sa.getMateria(q, getActivity()));
                    txtAula4.setText(sa.getAula(q, getActivity()));
                    break;
                case 5:
                    txtMat5.setText(sa.getMateria(q, getActivity()));
                    txtAula5.setText(sa.getAula(q, getActivity()));
                    break;
                case 6:
                    txtMat6.setText(sa.getMateria(q, getActivity()));
                    txtAula6.setText(sa.getAula(q, getActivity()));
                    break;
                case 7:
                    txtMat7.setText(sa.getMateria(q, getActivity()));
                    txtAula7.setText(sa.getAula(q, getActivity()));
                    break;
                case 8:
                    txtMat8.setText(sa.getMateria(q, getActivity()));
                    txtAula8.setText(sa.getAula(q, getActivity()));
                    break;
                case 9:
                    txtMat9.setText(sa.getMateria(q, getActivity()));
                    txtAula9.setText(sa.getAula(q, getActivity()));
                    break;
                case 10:
                    txtMat10.setText(sa.getMateria(q, getActivity()));
                    txtAula10.setText(sa.getAula(q, getActivity()));
                    break;
                case 11:
                    txtMat11.setText(sa.getMateria(q, getActivity()));
                    txtAula11.setText(sa.getAula(q, getActivity()));
                    break;
            }
            n++;
            inc--;
        }
    }
}
