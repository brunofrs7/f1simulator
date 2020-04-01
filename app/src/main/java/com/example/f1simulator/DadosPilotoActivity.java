package com.example.f1simulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DadosPilotoActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent i;
    private ViewHolder viewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_piloto);

        viewHolder.iv_dadosPiloto_foto = findViewById(R.id.iv_dadosPiloto_foto);
        viewHolder.et_dadosPiloto_nome = findViewById(R.id.et_dadosPiloto_nome);
        viewHolder.et_dadosPiloto_email = findViewById(R.id.et_dadosPiloto_email);
        viewHolder.et_dadosPiloto_idade = findViewById(R.id.et_dadosPiloto_idade);
        viewHolder.et_dadosPiloto_nacionalidade = findViewById(R.id.et_dadosPiloto_nacionalidade);
        viewHolder.et_dadosPiloto_numero = findViewById(R.id.et_dadosPiloto_numero);
        viewHolder.et_dadosPiloto_valor = findViewById(R.id.et_dadosPiloto_valor);
        viewHolder.bt_dadosPiloto_editar = findViewById(R.id.bt_dadosPiloto_editar);
        viewHolder.bt_dadosPiloto_eliminar = findViewById(R.id.bt_dadosPiloto_eliminar);
        viewHolder.bt_dadosPiloto_ok = findViewById(R.id.bt_dadosPiloto_ok);
        viewHolder.bt_dadosPiloto_cancelar = findViewById(R.id.bt_dadosPiloto_cancelar);
        viewHolder.ll_bts_cancelar_ok = findViewById(R.id.ll_bts_cancelar_ok);
        viewHolder.ll_bts_editar_eliminar = findViewById(R.id.ll_bts_editar_eliminar);


        viewHolder.bt_dadosPiloto_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.GONE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.VISIBLE);
                CamposEditaveis();
            }
        });
        viewHolder.bt_dadosPiloto_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.bt_dadosPiloto_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.VISIBLE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.GONE);
                CamposNaoEditaveis();
            }
        });
        viewHolder.bt_dadosPiloto_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.VISIBLE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.GONE);
                CamposNaoEditaveis();
            }
        });


        i = getIntent();
        String path = i.getExtras().getString("path");
        String id = path.substring(path.indexOf('/') + 1);

        DocumentReference docRef = db.collection("Piloto").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Toast.makeText(DadosPilotoActivity.this, "Dados lidos com sucesso", Toast.LENGTH_SHORT).show();
                        viewHolder.et_dadosPiloto_nome.setText(documentSnapshot.getData().get("nome").toString());
                        String nome = documentSnapshot.getData().get("nome").toString();
                        nome = nome.replace(" ", "");
                        nome = nome.toLowerCase();

                        String equipa = documentSnapshot.getData().get("equipa").toString();
                        equipa = equipa.replace(" ", "");
                        equipa = equipa.toLowerCase();

                        viewHolder.et_dadosPiloto_email.setText(nome + "@" + equipa + ".com");
                        viewHolder.et_dadosPiloto_idade.setText(documentSnapshot.getData().get("idade").toString());
                        viewHolder.et_dadosPiloto_nacionalidade.setText(documentSnapshot.getData().get("nacionalidade").toString());
                        viewHolder.et_dadosPiloto_numero.setText(documentSnapshot.getData().get("numero").toString());
                        viewHolder.et_dadosPiloto_valor.setText(documentSnapshot.getData().get("valor").toString());
                    } else {
                        Toast.makeText(DadosPilotoActivity.this, "Erro ao apresentar dados", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(DadosPilotoActivity.this, "Erro ao apresentar dados", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void CamposNaoEditaveis() {
        viewHolder.et_dadosPiloto_numero.setEnabled(false);
        viewHolder.et_dadosPiloto_valor.setEnabled(false);
        viewHolder.et_dadosPiloto_nacionalidade.setEnabled(false);
        viewHolder.et_dadosPiloto_idade.setEnabled(false);
        viewHolder.et_dadosPiloto_email.setEnabled(false);
        viewHolder.et_dadosPiloto_nome.setEnabled(false);
    }

    private void CamposEditaveis() {
        viewHolder.et_dadosPiloto_numero.setEnabled(true);
        viewHolder.et_dadosPiloto_valor.setEnabled(true);
        viewHolder.et_dadosPiloto_nacionalidade.setEnabled(true);
        viewHolder.et_dadosPiloto_idade.setEnabled(true);
        viewHolder.et_dadosPiloto_email.setEnabled(true);
        viewHolder.et_dadosPiloto_nome.setEnabled(true);
    }

    private class ViewHolder {
        ImageView iv_dadosPiloto_foto;
        EditText et_dadosPiloto_nome, et_dadosPiloto_numero, et_dadosPiloto_nacionalidade, et_dadosPiloto_idade, et_dadosPiloto_valor, et_dadosPiloto_email;
        Button bt_dadosPiloto_editar, bt_dadosPiloto_eliminar;
        Button bt_dadosPiloto_ok, bt_dadosPiloto_cancelar;
        LinearLayout ll_bts_cancelar_ok, ll_bts_editar_eliminar;
    }
}
