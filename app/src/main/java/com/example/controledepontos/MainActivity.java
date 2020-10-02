package com.example.controledepontos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_minimo_temporada,txt_maximo_temporada,txt_quebra_recorde_minimo,txt_quebra_recorde_maximo;
    Button btnConsultarJogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        txt_minimo_temporada = findViewById(R.id.txt_min_temporada);
        txt_maximo_temporada = findViewById(R.id.txt_max_temporada);
        txt_quebra_recorde_minimo = findViewById(R.id.txt_recorde_minimo);
        txt_quebra_recorde_maximo = findViewById(R.id.txt_recorde_maximo);

        carregarEstatisticas();

    }

    //Método responsavel por fazer uma ligação entre a tela dos jogos e então exibi-la ao usuário.
    public void ConsultarJogos(View view){
        Intent intent =  new Intent(MainActivity.this,Jogos.class);
        startActivity(intent);
    }

    //Esse método é responsavel por carregar as estatísticas da temporadas na tela, buscando os objetos chave-valor nos arquivos locais do aplicativo..
    public void carregarEstatisticas(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.controledepontos", Context.MODE_PRIVATE);
        int minimo_temporada= sharedPreferences.getInt("minimo_temporada",0);
        int maximo_temporada= sharedPreferences.getInt("maximo_temporada",0);
        int quebra_recorde_minimo= sharedPreferences.getInt("quebra_recorde_minimo",0);
        int quebra_recorde_maximo= sharedPreferences.getInt("quebra_recorde_maximo",0);

        txt_minimo_temporada.setText(Integer.toString(minimo_temporada));
        txt_maximo_temporada.setText(Integer.toString(maximo_temporada));
        txt_quebra_recorde_minimo.setText(Integer.toString(quebra_recorde_minimo));
        txt_quebra_recorde_maximo.setText(Integer.toString(quebra_recorde_maximo));
    }

    //Esse método atualiza a tela toda vez que o usuário retornar para ela.
    @Override
    protected void onResume() {
        carregarEstatisticas();
        super.onResume();
    }
}