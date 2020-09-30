package com.example.controledepontos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class JogosDAO {

    private SQLiteDatabase banco;
    private Conexao conexao;

    public JogosDAO(Context context){
        this.conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    public void excluirJogo(Jogo jogo){
        String[] id = new String[]{Integer.toString(jogo.getId())};
        banco.delete("jogos","id=?", id);
    }

    public long inserirJogo(Jogo jogo){
        ContentValues values = new ContentValues();
        values.put("pontuacao",jogo.getPontuacao());
        values.put("data",jogo.getData());
        values.put("titulo",jogo.getTitulo());
        return banco.insert("jogos",null, values);
    }

    public void atualizarJogo(Jogo jogo){
        ContentValues values = new ContentValues();
        values.put("id",jogo.getId());
        values.put("pontuacao",jogo.getPontuacao());
        values.put("data",jogo.getData());
        values.put("titulo",jogo.getTitulo());
        String[] id = new String[]{Integer.toString(jogo.getId())};
        banco.update("jogos", values, "id=?", id);
    }

    public ArrayList obterJogos(){
        ArrayList<Jogo> jogos = new ArrayList<>();
        Cursor cursor = banco.query("jogos", new String[]{"id","pontuacao","data","titulo"}, (String)null, (String[])null, (String)null, (String)null, (String)null);

        while (cursor.moveToNext()){
            Jogo jogo = new Jogo();
            jogo.setId(cursor.getInt(0));
            jogo.setPontuacao(cursor.getInt(1));
            jogo.setData((cursor.getString(2)));
            jogo.setTitulo((cursor.getString(3)));
            jogos.add(jogo);
        }

        return jogos;
    }

    public  void limparJogos(){
        banco.execSQL("delete from jogos");
    }

}
