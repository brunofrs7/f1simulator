package com.example.f1simulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistoActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        mViewHolder.et_email = findViewById(R.id.et_registo_email);
        mViewHolder.et_password = findViewById(R.id.et_registo_password);
        mViewHolder.et_passwordrep = findViewById(R.id.et_registo_password1);
        mViewHolder.bt_registar = findViewById(R.id.bt_registo_registar);

        mAuth = FirebaseAuth.getInstance();

        mViewHolder.bt_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mViewHolder.et_email.getText().toString();
                String password = mViewHolder.et_password.getText().toString();
                String password1 = mViewHolder.et_passwordrep.getText().toString();

                if(!password.equals(password1)){
                    Toast.makeText(RegistoActivity.this,"Palavra-passe não coincide", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistoActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegistoActivity.this, "Utilizador registado com sucesso",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegistoActivity.this, "Registo inválido",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private class ViewHolder {
        EditText et_email, et_password, et_passwordrep;
        Button bt_registar;
    }
}
