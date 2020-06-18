package com.example.f1simulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaImagemPilotoAdapter extends RecyclerView.Adapter<ListaImagemPilotoAdapter.ListaImagemPilotoViewHolder> {

    public Context context;
    List<Imagem> listaImagens;

    public ListaImagemPilotoAdapter(Context context, List<Imagem> listaImagens) {
        this.context = context;
        this.listaImagens = listaImagens;
    }

    @NonNull
    @Override
    public ListaImagemPilotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.imagempiloto_item, parent, false);
        return new ListaImagemPilotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaImagemPilotoViewHolder holder, int position) {
        Imagem imagem = listaImagens.get(position);
        Picasso.get().load(imagem.getImagemUrl()).fit().centerCrop().into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return listaImagens.size();
    }

    public class ListaImagemPilotoViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagem;

        public ListaImagemPilotoViewHolder(@NonNull View itemView) {
            super(itemView);

            imagem = itemView.findViewById(R.id.iv_imagempiloto_imagem);
        }
    }
}
