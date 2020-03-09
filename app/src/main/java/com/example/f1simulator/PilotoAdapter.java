package com.example.f1simulator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PilotoAdapter extends FirestoreRecyclerAdapter<Piloto, PilotoAdapter.PilotoHolder> {

    private OnItemClickListener listener;

    public PilotoAdapter(@NonNull FirestoreRecyclerOptions<Piloto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PilotoHolder holder, int position, @NonNull Piloto model) {
        holder.tv_numero.setText(String.valueOf(model.getNumero()));
        holder.tv_nome.setText(model.getNome());
    }

    @NonNull
    @Override
    public PilotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.piloto_item, parent, false);
        return new PilotoHolder(v);
    }

    class PilotoHolder extends RecyclerView.ViewHolder {

        TextView tv_numero;
        TextView tv_nome;

        public PilotoHolder(@NonNull View itemView) {
            super(itemView);
            tv_numero = itemView.findViewById(R.id.tv_pilotoItem_numero);
            tv_nome = itemView.findViewById(R.id.tv_pilotoItem_nome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(pos), pos);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
