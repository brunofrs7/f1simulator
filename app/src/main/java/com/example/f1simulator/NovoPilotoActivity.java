package com.example.f1simulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NovoPilotoActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_piloto);

        mViewHolder.bt_cancelar = findViewById(R.id.bt_novoPiloto_cancelar);
        mViewHolder.bt_gravar = findViewById(R.id.bt_novoPiloto_gravar);
        mViewHolder.et_equipa = findViewById(R.id.et_novoPiloto_equipa);
        mViewHolder.et_nome = findViewById(R.id.et_novoPiloto_nome);
        mViewHolder.et_numero = findViewById(R.id.et_novoPiloto_numero);
        mViewHolder.et_nacionalidade = findViewById(R.id.et_novoPiloto_nacionalidade);
        mViewHolder.et_idade = findViewById(R.id.et_novoPiloto_idade);
        mViewHolder.et_valor = findViewById(R.id.et_novoPiloto_valor);
        mViewHolder.iv_foto = findViewById(R.id.iv_novoPiloto_foto);

        mViewHolder.bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewHolder.bt_gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = mViewHolder.et_nome.getText().toString();
                int numero = Integer.parseInt(mViewHolder.et_numero.getText().toString());
                String nacionalidade = mViewHolder.et_nacionalidade.getText().toString();
                int idade = Integer.parseInt(mViewHolder.et_idade.getText().toString());
                Double valor = Double.parseDouble(mViewHolder.et_valor.getText().toString());
                String equipa = mViewHolder.et_equipa.getText().toString();

                //IMAGEM

                if (nome.trim().isEmpty() || nacionalidade.trim().isEmpty() || equipa.trim().isEmpty()) {
                    Toast.makeText(NovoPilotoActivity.this, "Dados do piloto não preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                CollectionReference reference = FirebaseFirestore.getInstance().collection("Piloto");
                reference.add(new Piloto(nome, numero, nacionalidade, idade, valor, equipa));
                Toast.makeText(NovoPilotoActivity.this, "Piloto criado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private static class ViewHolder {
        ImageView iv_foto;
        EditText et_nome, et_numero, et_nacionalidade, et_idade, et_valor, et_equipa;
        Button bt_gravar, bt_cancelar;
    }
}
