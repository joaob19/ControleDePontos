package com.example.controledepontos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterJogo extends ArrayAdapter {

    Context context;
    ArrayList<Jogo> jogos;

    public AdapterJogo(Context context,ArrayList<Jogo> jogos) {
        super(context, R.layout.layout_jogo,jogos);
        this.context=context;
        this.jogos = new ArrayList<Jogo>(jogos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_jogo,null);

        TextView txtTitulo = view.findViewById(R.id.txtDataJogo);
        TextView txtPontuacao = view.findViewById(R.id.txtPontosDoJogo);

        txtTitulo.setText("Jogo do dia "+ jogos.get(position).getData());
        txtPontuacao.setText("Pontuação: "+ jogos.get(position).getPontuacao());

        return view;
    }
}
