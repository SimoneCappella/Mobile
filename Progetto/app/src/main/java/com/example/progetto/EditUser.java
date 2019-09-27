package com.example.progetto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class EditUser extends AppCompatActivity implements View.OnClickListener {
    TextView txtNewusr;
    Button btnEdit;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        back = findViewById(R.id.back);
        txtNewusr = findViewById(R.id.txtNewPass);
        btnEdit = findViewById(R.id.btnEditUser);
        back.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btnEditUser:
                String method = "edit";
                String newusr = txtNewusr.getText().toString();
                String auth = "";
                SupportTask supportTask = new SupportTask(EditUser.this);
                if (newusr.length() >= 3)
                {
                    try
                    {
                        String url = "http://mobileproject.altervista.org/editusername.php";
                        auth = supportTask.execute(method, FirstActivity.login_name, newusr, url).get();
                    }catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }catch (InterruptedException a)
                    {
                        a.printStackTrace();
                    }
                    switch(auth) {
                        case "modificato":
                            Toast.makeText(this, "Username cambiato con successo!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(this, "Per cambiare nome nella home riavviare l'applicazione.", Toast.LENGTH_SHORT).show();
                            break;
                        case "in uso":
                            Toast.makeText(this, "Username già in uso!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(this, "Si è verificato un errore, preghiamo di riprovare più tardi.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }else
                {
                    Toast.makeText(this, "L'username deve essere minimo di 3 caratteri.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
