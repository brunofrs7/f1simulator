package com.example.f1simulator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;


public class NovoPilotoActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final int PICK_REQUEST = 1;
    private Uri uri_imagem;
    private String nome = "", nacionalidade = "", equipa = "", email = "", foto = "";
    private int numero = 0, idade = 0;
    private double valor = 0;

    private StorageTask uploadTask;

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
        mViewHolder.et_email = findViewById(R.id.et_novoPiloto_email);
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
                nome = mViewHolder.et_nome.getText().toString();
                try {
                    numero = Integer.parseInt(mViewHolder.et_numero.getText().toString());
                } catch (Exception e) {
                    numero = 0;
                }
                nacionalidade = mViewHolder.et_nacionalidade.getText().toString();
                try {
                    idade = Integer.parseInt(mViewHolder.et_idade.getText().toString());
                } catch (Exception e) {
                    idade = 0;
                }
                try {
                    valor = Double.parseDouble(mViewHolder.et_valor.getText().toString());
                } catch (Exception e) {
                    valor = 0;
                }
                equipa = mViewHolder.et_equipa.getText().toString();
                email = mViewHolder.et_email.getText().toString();
                foto = "";

                //IMAGEM

                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(NovoPilotoActivity.this, "Imagem em upload, aguarde", Toast.LENGTH_SHORT).show();
                } else {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("pilotos");
                    if (uri_imagem != null) {
                        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri_imagem));
                        uploadTask = fileRef.putFile(uri_imagem)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(NovoPilotoActivity.this, "Upload da imagem com sucesso", Toast.LENGTH_SHORT).show();

                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uri.isComplete()) ;
                                        foto = uri.getResult().toString();

                                        if (nome.trim().isEmpty() || nacionalidade.trim().isEmpty() || equipa.trim().isEmpty()) {
                                            Toast.makeText(NovoPilotoActivity.this, "Dados do piloto não preenchidos", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        CollectionReference reference = FirebaseFirestore.getInstance().collection("Piloto");
                                        reference.add(new Piloto(nome, numero, nacionalidade, idade, valor, email, foto, equipa));
                                        Toast.makeText(NovoPilotoActivity.this, "Piloto criado com sucesso", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NovoPilotoActivity.this, "Erro ao fazer o upload da imagem", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(NovoPilotoActivity.this, "Imagem não selecionada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mViewHolder.iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, PICK_REQUEST);
            }
        });
    }

    private String getFileExtension(Uri uri_imagem) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri_imagem));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri_imagem = data.getData();
            Picasso.get().load(uri_imagem).into(mViewHolder.iv_foto);

        }

    }

    private static class ViewHolder {
        ImageView iv_foto;
        EditText et_nome, et_numero, et_nacionalidade, et_idade, et_valor, et_equipa, et_email;
        Button bt_gravar, bt_cancelar;
    }
}
