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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class DadosPilotoActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent i;
    private ViewHolder viewHolder = new ViewHolder();
    String id = "";
    DocumentReference docRef;
    String nome, numero, nacionalidade, idade, valor, equipa, foto = "";
    boolean editable = false;
    private static final int PICK_REQUEST = 1;
    private Uri uri_imagem;
    private StorageTask uploadTask;


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
        viewHolder.et_dadosPiloto_equipa = findViewById(R.id.et_dadosPiloto_equipa);
        viewHolder.bt_dadosPiloto_editar = findViewById(R.id.bt_dadosPiloto_editar);
        viewHolder.bt_dadosPiloto_eliminar = findViewById(R.id.bt_dadosPiloto_eliminar);
        viewHolder.bt_dadosPiloto_ok = findViewById(R.id.bt_dadosPiloto_ok);
        viewHolder.bt_dadosPiloto_cancelar = findViewById(R.id.bt_dadosPiloto_cancelar);
        viewHolder.bt_dadosPiloto_enviarEmail = findViewById(R.id.bt_dadosPiloto_enviarEmail);
        viewHolder.ll_bts_cancelar_ok = findViewById(R.id.ll_bts_cancelar_ok);
        viewHolder.ll_bts_editar_eliminar = findViewById(R.id.ll_bts_editar_eliminar);

        viewHolder.bt_dadosPiloto_enviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.et_dadosPiloto_email.getText().toString().trim().isEmpty()){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("mailto:"+viewHolder.et_dadosPiloto_email.getText().toString()));
                    startActivity(i);
                }
            }
        });

        viewHolder.bt_dadosPiloto_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.GONE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.VISIBLE);
                CamposEditaveis();
                nome = viewHolder.et_dadosPiloto_nome.getText().toString();
                numero = viewHolder.et_dadosPiloto_numero.getText().toString();
                nacionalidade = viewHolder.et_dadosPiloto_nacionalidade.getText().toString();
                idade = viewHolder.et_dadosPiloto_idade.getText().toString();
                valor = viewHolder.et_dadosPiloto_valor.getText().toString();
                equipa = viewHolder.et_dadosPiloto_equipa.getText().toString();
            }
        });

        viewHolder.bt_dadosPiloto_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.delete();
                finish();
            }
        });

        viewHolder.bt_dadosPiloto_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.VISIBLE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.GONE);
                CamposNaoEditaveis();

                UploadFoto();

            }
        });

        viewHolder.bt_dadosPiloto_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.ll_bts_editar_eliminar.setVisibility(View.VISIBLE);
                viewHolder.ll_bts_cancelar_ok.setVisibility(View.GONE);
                CamposNaoEditaveis();
                viewHolder.et_dadosPiloto_nome.setText(nome);
                viewHolder.et_dadosPiloto_numero.setText(numero);
                viewHolder.et_dadosPiloto_nacionalidade.setText(nacionalidade);
                viewHolder.et_dadosPiloto_idade.setText(idade);
                viewHolder.et_dadosPiloto_valor.setText(valor);
                viewHolder.et_dadosPiloto_equipa.setText(equipa);
            }
        });

        viewHolder.iv_dadosPiloto_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editable) {
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(i, PICK_REQUEST);
                }
            }
        });


        i = getIntent();
        String path = i.getExtras().getString("path");
        id = path.substring(path.indexOf('/') + 1);

        docRef = db.collection("Piloto").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        //Toast.makeText(DadosPilotoActivity.this, "Dados lidos com sucesso", Toast.LENGTH_SHORT).show();
                        viewHolder.et_dadosPiloto_nome.setText(documentSnapshot.getData().get("nome").toString());
                        viewHolder.et_dadosPiloto_idade.setText(documentSnapshot.getData().get("idade").toString());
                        viewHolder.et_dadosPiloto_nacionalidade.setText(documentSnapshot.getData().get("nacionalidade").toString());
                        viewHolder.et_dadosPiloto_numero.setText(documentSnapshot.getData().get("numero").toString());
                        viewHolder.et_dadosPiloto_valor.setText(documentSnapshot.getData().get("valor").toString());
                        viewHolder.et_dadosPiloto_equipa.setText(documentSnapshot.getData().get("equipa").toString());
                        try {
                            viewHolder.et_dadosPiloto_email.setText(documentSnapshot.getData().get("email").toString());
                        }
                        catch(Exception e){}

                        try {
                            Picasso.get().load(documentSnapshot.getData().get("foto").toString()).into(viewHolder.iv_dadosPiloto_foto);
                        } catch (Exception e) {

                        }
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

    private void UploadFoto() {
        if (uploadTask != null && uploadTask.isInProgress()) {
            Toast.makeText(DadosPilotoActivity.this, "Imagem em upload, aguarde", Toast.LENGTH_SHORT).show();
        } else {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("pilotos");
            if (uri_imagem != null) {
                StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri_imagem));
                uploadTask = fileRef.putFile(uri_imagem)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(DadosPilotoActivity.this, "Upload da imagem com sucesso", Toast.LENGTH_SHORT).show();

                                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uri.isComplete()) ;
                                foto = uri.getResult().toString();
                                UpdatePiloto();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DadosPilotoActivity.this, "Erro ao fazer o upload da imagem", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                UpdatePiloto();
            }
        }
    }

    private void UpdatePiloto() {
        docRef.update("nome", viewHolder.et_dadosPiloto_nome.getText().toString());
        docRef.update("email", viewHolder.et_dadosPiloto_email.getText().toString());
        docRef.update("idade", Integer.parseInt(viewHolder.et_dadosPiloto_idade.getText().toString()));
        docRef.update("nacionalidade", viewHolder.et_dadosPiloto_nacionalidade.getText().toString());
        docRef.update("valor", Double.parseDouble(viewHolder.et_dadosPiloto_valor.getText().toString()));
        docRef.update("numero", Integer.parseInt(viewHolder.et_dadosPiloto_numero.getText().toString()));
        docRef.update("equipa", viewHolder.et_dadosPiloto_equipa.getText().toString());
        docRef.update("foto", foto);
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
            Picasso.get().load(uri_imagem).into(viewHolder.iv_dadosPiloto_foto);
        }
    }

    private void CamposNaoEditaveis() {
        editable = false;
        viewHolder.et_dadosPiloto_numero.setEnabled(false);
        viewHolder.et_dadosPiloto_valor.setEnabled(false);
        viewHolder.et_dadosPiloto_nacionalidade.setEnabled(false);
        viewHolder.et_dadosPiloto_idade.setEnabled(false);
        viewHolder.et_dadosPiloto_email.setEnabled(false);
        viewHolder.et_dadosPiloto_nome.setEnabled(false);
        viewHolder.et_dadosPiloto_equipa.setEnabled(false);
    }

    private void CamposEditaveis() {
        editable = true;
        viewHolder.et_dadosPiloto_numero.setEnabled(true);
        viewHolder.et_dadosPiloto_valor.setEnabled(true);
        viewHolder.et_dadosPiloto_nacionalidade.setEnabled(true);
        viewHolder.et_dadosPiloto_idade.setEnabled(true);
        viewHolder.et_dadosPiloto_email.setEnabled(true);
        viewHolder.et_dadosPiloto_nome.setEnabled(true);
        viewHolder.et_dadosPiloto_equipa.setEnabled(true);
    }

    private class ViewHolder {
        ImageView iv_dadosPiloto_foto;
        EditText et_dadosPiloto_nome, et_dadosPiloto_numero, et_dadosPiloto_nacionalidade, et_dadosPiloto_idade, et_dadosPiloto_valor, et_dadosPiloto_email, et_dadosPiloto_equipa;
        Button bt_dadosPiloto_editar, bt_dadosPiloto_eliminar;
        Button bt_dadosPiloto_ok, bt_dadosPiloto_cancelar, bt_dadosPiloto_enviarEmail;
        LinearLayout ll_bts_cancelar_ok, ll_bts_editar_eliminar;
    }
}
