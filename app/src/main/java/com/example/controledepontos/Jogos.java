package com.example.controledepontos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Jogos extends AppCompatActivity {

    ArrayList<Jogo> array_jogos;
    ArrayAdapter adapter_jogos = null;
    JogosDAO jogosDAO;

    EditText txtPontuacaoJogo;
    ListView lista_jogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        jogosDAO = new JogosDAO(this);
        txtPontuacaoJogo = findViewById(R.id.txtPontuacaoJogo);
        lista_jogos = findViewById(R.id.lista_jogos);
        carregarJogos();
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
                        Toast.makeText(Jogos.this, "Jogo excluído.", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.show();

                return true;
            }
        });

    }

    public void carregarJogos(){
        array_jogos = jogosDAO.obterJogos();
        adapter_jogos = new AdapterJogo(this,array_jogos);
        lista_jogos.setAdapter(adapter_jogos);
    }

    public void  adicionarJogo(View view){
        Jogo jogo = new Jogo();
        int pontuacao = Integer.parseInt(txtPontuacaoJogo.getText().toString());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String data = df.format(c);

        jogo.setData(data);
        jogo.setPontuacao(pontuacao);

        jogosDAO.inserirJogo(jogo);
        carregarJogos();

    }

}