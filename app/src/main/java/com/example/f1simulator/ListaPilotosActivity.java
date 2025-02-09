package com.example.f1simulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaPilotosActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pilotoRef = db.collection("Piloto");
    private PilotoAdapter adapter;
    private FloatingActionButton bt_listaPilotos_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pilotos);
        bt_listaPilotos_add = findViewById(R.id.bt_listaPilotos_add);
        setUpRecyclerView();

        adapter.setOnItemClickListener(new PilotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int pos) {
                Piloto piloto = documentSnapshot.toObject(Piloto.class);
                String path = documentSnapshot.getReference().getPath();

                //Toast.makeText(ListaPilotosActivity.this, path, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ListaPilotosActivity.this, DadosPilotoActivity.class);
                i.putExtra("path", path);
                startActivity(i);
            }
        });


        bt_listaPilotos_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaPilotosActivity.this, NovoPilotoActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = pilotoRef.orderBy("numero", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Piloto> options = new FirestoreRecyclerOptions.Builder<Piloto>()
                .setQuery(query, Piloto.class).build();

        adapter = new PilotoAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_listaPilotos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
