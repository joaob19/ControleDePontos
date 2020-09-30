package com.example.controledepontos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Jogos extends AppCompatActivity implements DialogCriarJogo.DialogCriarJogoListener {

    ArrayList<Jogo> array_jogos;
    ArrayAdapter adapter_jogos = null;
    JogosDAO jogosDAO;
    TextView txtMensagem;
    ListView lista_jogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seus Jogos");

        txtMensagem = findViewById(R.id.txtMensagemJogos);
        jogosDAO = new JogosDAO(this);
        lista_jogos = findViewById(R.id.lista_jogos);
        carregarJogos();
        verificarJogos();
        lista_jogos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder adb= new AlertDialog.Builder(Jogos.this);
                adb.setTitle("Excluir jogo");
                adb.setMessage("Deseja excluir o registro desse jogo?");
                final int positionToMove = position;
                adb.setNegativeButton("NÂo",null);
                adb.setPositiveButton("SIM",new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JogosDAO jogosDAO = new JogosDAO(Jogos.this);
                        jogosDAO.excluirJogo(array_jogos.get(position));
                        carregarJogos();
                        verificarJogos();
                        Toast.makeText(Jogos.this, "Jogo excluído.", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.show();

                return true;
            }
        });

    }

    private void verificarJogos() {

        if(array_jogos.size()>0){
            txtMensagem.setText(null);
        }
        else{
            txtMensagem.setText(R.string.mensagem_jogos);
            zerarEstatisticas();
        }

    }

    public void zerarEstatisticas(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.controledepontos",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("maximo_temporada",0).apply();
        sharedPreferences.edit().putInt("minimo_temporada",0).apply();
        sharedPreferences.edit().putInt("quebra_recorde_minimo",0).apply();
        sharedPreferences.edit().putInt("quebra_recorde_maximo",0).apply();
    }


    public void carregarJogos(){
        array_jogos = jogosDAO.obterJogos();
        adapter_jogos = new AdapterJogo(this,array_jogos);
        lista_jogos.setAdapter(adapter_jogos);
    }

    public void adicionarJogo(View view){
        DialogCriarJogo dialogCriarJogo = new DialogCriarJogo();
        dialogCriarJogo.show(getSupportFragmentManager(),"Criar jogo");
    }

    public void verificaPontuacao(int pontuacao){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.controledepontos",Context.MODE_PRIVATE);
        int minimo_temporada= sharedPreferences.getInt("minimo_temporada",0);
        int maximo_temporada= sharedPreferences.getInt("maximo_temporada",0);
        int quebra_recorde_minimo= sharedPreferences.getInt("quebra_recorde_minimo",0);
        int quebra_recorde_maximo= sharedPreferences.getInt("quebra_recorde_maximo",0);
        boolean primeiro_jogo = sharedPreferences.getBoolean("primeiro_jogo",true);

        if(primeiro_jogo){
            if(pontuacao>0){
                maximo_temporada=pontuacao;
                minimo_temporada=pontuacao;
                sharedPreferences.edit().putInt("maximo_temporada",maximo_temporada).apply();
                sharedPreferences.edit().putInt("minimo_temporada",minimo_temporada).apply();
            }
            sharedPreferences.edit().putBoolean("primeiro_jogo",false).apply();
        }
        else{
            if((pontuacao>minimo_temporada)&&(pontuacao>maximo_temporada)){
                maximo_temporada=pontuacao;
                quebra_recorde_maximo++;
                sharedPreferences.edit().putInt("maximo_temporada",maximo_temporada).apply();
                sharedPreferences.edit().putInt("quebra_recorde_maximo",quebra_recorde_maximo).apply();
            }
            else if((pontuacao<maximo_temporada)&&(pontuacao<minimo_temporada)){
                minimo_temporada=pontuacao;
                quebra_recorde_minimo++;
                sharedPreferences.edit().putInt("minimo_temporada",minimo_temporada).apply();
                sharedPreferences.edit().putInt("quebra_recorde_minimo",quebra_recorde_minimo).apply();
            }
        }
    }

    @Override
    public void salvarJogo(Jogo jogo) {
        jogosDAO.inserirJogo(jogo);
        verificaPontuacao(jogo.getPontuacao());
        carregarJogos();
        verificarJogos();
    }
}