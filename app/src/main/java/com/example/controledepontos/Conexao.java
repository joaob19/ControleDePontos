package com.example.controledepontos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    //Esta classe é responsável por fazer a conexão do aplicativo com o banco de dados nativo do android
    //chamado SqLite

    //Estes atributos são informações do banco de dados, seu nome e a sua versão.
    //Sempre que for feita uma atualização no banco a versão do banco deverá ser atualizada

    private static final String nomeDoBanco = "Banco";
    private static final int versaoBanco = 1;

    //Este método é o Contrutor da classe que recebe como parâmetro um contexto.
    //Com isso ele pode criar um banco de dados para o app.
    public Conexao(Context context) {
        super(context, nomeDoBanco, (SQLiteDatabase.CursorFactory)null, versaoBanco);
    }

    //Este método será executado a primeira vez que o app for aberto.
    //Aqui ficarão os comandos sql para criação de tabelas

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jogos(id integer primary key,titulo varchar(50),pontuacao integer,data varchar(20))");
    }

    //Este método será executado cada vez que a versão do banco for modificada.
    //Aqui será onde serão feitas alterações para usuários que já tenham executado o app uma fez.
    //Além de ter que implementar comandos sql no método onCreate também será necessario escrever aqui para atualizar o banco dos que já possuem.


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
