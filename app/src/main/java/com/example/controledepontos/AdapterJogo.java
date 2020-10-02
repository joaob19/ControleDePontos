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
//Essa classe é responsável por coordenar como as informações de um objeto jogo serão exibidas na lista de jogos.
public class AdapterJogo extends ArrayAdapter {

    Context context;
    ArrayList<Jogo> jogos;
//Esse é o contrutor da classe que recebe como parâmetro um contexto e o ArrayList contendo os objetos Jogo.
    public AdapterJogo(Context context,ArrayList<Jogo> jogos) {
        super(context, R.layout.layout_jogo,jogos);
        this.context=context;
        this.jogos = new ArrayList<Jogo>(jogos);
    }

    //Esse método ira dizer como e em que campos cada informação de um objeto Jogo será exibida.
    //esse método relacionada cada objeto doArrayList com a respectivoa posição na lista de exibição, colocando-os na ordem que foram adicionados no banco.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_jogo,null);

        TextView txtTitulo = view.findViewById(R.id.txtTituloJogo);
        TextView txtData = view.findViewById(R.id.txtDataJogo);
        TextView txtPontuacao = view.findViewById(R.id.txtPontosDoJogo);

        txtTitulo.setText(jogos.get(position).getTitulo());
        txtPontuacao.setText("Pontuação: "+ jogos.get(position).getPontuacao());
        txtData.setText(jogos.get(position).getData());

        return view;
    }
}
