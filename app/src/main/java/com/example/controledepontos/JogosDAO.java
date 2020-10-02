package com.example.controledepontos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

//Essa classe é responsavel pelas interações feitas no banco de dados, fazendo o crud da aplicação.
public class JogosDAO {

    private SQLiteDatabase banco;
    private Conexao conexao;

    //Esse método é o construtor, que recebe como parâmetro um contexto. Após isso ele pode usar esse contexto para se conectar ao banco.
    public JogosDAO(Context context){
        this.conexao = new Conexao(context);
        this.banco = conexao.getWritableDatabase();
    }

    //Método responsável por excluir um jogo do banco.
    //Esse método usa como parâmetro um objeto Jogo, e então excluir o objeto no banco baseando-se no seu numero id.
    public void excluirJogo(Jogo jogo){
        String[] id = new String[]{Integer.toString(jogo.getId())};
        banco.delete("jogos","id=?", id);
    }

    //Método responsável por inserir um novo objeto Jogo no banco de dados.
    //Esse método recebe como parâmetro um objeto Jogo . após isso, ele armazena as informações em um objeto ContentValues atribuindo os dados do objeto aà uma chave.
    //Após isso, é retornado um long confirmando que o objeto foi inserido no banco usando o método insert() que recebe como parâmetro o nome da tabela,o numcolumnHack e os valor a serem adicionados.
    public long inserirJogo(Jogo jogo){
        ContentValues values = new ContentValues();
        values.put("pontuacao",jogo.getPontuacao());
        values.put("data",jogo.getData());
        values.put("titulo",jogo.getTitulo());
        return banco.insert("jogos",null, values);
    }

    //Método responsável por retornar um ArrayList contando todos os jogos cadastrados.
    //Para fazer a leitura dos dados no banco, é necessário utilizar um objeto Cursor, que possui um ponteiro capaz de apontar para linha de uma tabela especifica e identificar dados das colunas.
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
