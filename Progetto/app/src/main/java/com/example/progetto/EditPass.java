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

public class EditPass extends AppCompatActivity implements View.OnClickListener{

    TextView txtOldPass;
    TextView txtNewPass;
    Button btnChangePass;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        txtOldPass = findViewById(R.id.txtOldPass);
        txtNewPass = findViewById(R.id.txtNewPass);
        btnChangePass = findViewById(R.id.btnEditPass);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                v.startAnimation(buttonClick);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.btnEditPass:
                String method = "passedit";
                String oldpass = txtOldPass.getText().toString();
                String newpass = txtNewPass.getText().toString();
                String auth = "";
                SupportTask supportTask = new SupportTask(EditPass.this);
                if (oldpass.length() >= 3 && newpass.length() >= 3)
                {
                    try{
                        String url = "http://mobileproject.altervista.org/editpass.php";
                        auth = supportTask.execute(method, oldpass, newpass, url).get();
                    }catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }catch (InterruptedException a)
                    {
                        a.printStackTrace();
                    }
                    switch(auth)
                    {
                        case "updated":
                            Toast.makeText(this, "Password modificata con successo!", Toast.LENGTH_SHORT).show();
                            break;
                        case "pass errata":
                            Toast.makeText(this, "Password attuale errata!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(this, "Si è verificato un errore, preghiamo di riprovare più tardi.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }else
                {
                    Toast.makeText(this, "I campi devono contenere minimo 3 caratteri.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
