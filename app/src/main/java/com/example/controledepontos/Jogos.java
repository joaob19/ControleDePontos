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

//Essa é a classe responsável por exibir e adicionar novos jogos.
//Dentro dessa classe também há vários métodos responsaveis por fazer requisições e verificações de dados.
public class Jogos extends AppCompatActivity implements DialogCriarJogo.DialogCriarJogoListener {

    ArrayList<Jogo> array_jogos;
    ArrayAdapter adapter_jogos = null;
    JogosDAO jogosDAO;
    TextView txtMensagem;
    ListView lista_jogos;

    //o método OnCreate é responsével por abrir a tela "Jogos" e atribuir funções ao compotenentes da tela.
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

        //Esse método exibirá uma caixa de dialogo perguntando se o usuário deseja apagar um jogo após ficar pressionando o mesmo por algum tempo.
        lista_jogos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb= new AlertDialog.Builder(Jogos.this);
                adb.setTitle("Excluir jogo");
                adb.setMessage("Deseja excluir o registro desse jogo?");
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

    //Esse método verifica se há algum jogo adionado no banco. Caso não haja nenhum jogo adionado, será exibida uma mensagem instrucionando o usuário a adicionar um jogo.
    //TAmbé serão limpos todos os dados do bancoe as estatisticas da temporada caso não haja nenhum jogo adicionado no banco.
    private void verificarJogos() {
        if(array_jogos.size()>0){
            txtMensagem.setText(null);
        }
        else{
            txtMensagem.setText(R.string.mensagem_jogos);
            zerarEstatisticas();
        }

    }

    //Esse método exibirá uma caixa de dialogo perguntando se o usuário deseja apagar todos os dados do aplicativo, comojogos e recorder.
    public void limparDados(View view){
        AlertDialog.Builder adb= new AlertDialog.Builder(Jogos.this);
        adb.setTitle("Limpar dados");
        adb.setMessage("Se você desejar limpar os dados, todos os dados incluindo jogos e estatísticas serão apagados.");
        adb.setNegativeButton("NÂo",null);
        adb.setPositiveButton("SIM",new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                zerarEstatisticas();
                jogosDAO.limparJogos();
                carregarJogos();
                Toast.makeText(Jogos.this, "Todos os dados foram limpos com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
        adb.show();
    }

    //Este método é responsavel por zerar todas as estatísticas da temporada, zerando as informações que foram salvas em SharedPreferences.
    //Por as estatísticas serem algo simples, reseolvi salva-las como objetos chave-valor aoinvés de criar uma tabela nova para salvar poucos dados.
    public void zerarEstatisticas(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.controledepontos",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("maximo_temporada",0).apply();
        sharedPreferences.edit().putInt("minimo_temporada",0).apply();
        sharedPreferences.edit().putInt("quebra_recorde_minimo",0).apply();
        sharedPreferences.edit().putInt("quebra_recorde_maximo",0).apply();
        sharedPreferences.edit().putBoolean("primeiro_jogo",true).apply();
    }

    //Esse método é responsávelpor carregar os jogos contidos no banco. Este método popula um ArrayList com os jogos salvos e então cria um adaptador para a lista de exibição.
    //O adaptador é reponsavel por dizer como cada linha da lista apresentará as informações do objeto.
    public void carregarJogos(){
        array_jogos = jogosDAO.obterJogos();
        adapter_jogos = new AdapterJogo(this,array_jogos);
        lista_jogos.setAdapter(adapter_jogos);
        verificarJogos();
    }

    //Método responsável por criar uma janela onde seráo inseridos os dados para adionar um novo jogo.
    //Esta janela é o "DialogCriarJogo"
    public void adicionarJogo(View view){
        DialogCriarJogo dialogCriarJogo = new DialogCriarJogo();
        dialogCriarJogo.show(getSupportFragmentManager(),"Criar jogo");
    }

    //Esse método é responsável por fazer uma verificação antes de salvar um novo jogo no banco.
    //Essa verificação consiste em verificar se é o primeiro jog, e se a pontuação quebrou algum recorde.
    //Após as verificações forem feitas, as estatísticas da temporada são atualizadas.
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

    //Interface que recebe um jogo após o botão adionar do DialogCriarJogo ser acionado.
    //Após receber um objeto jogo, ele é salvo no banco de dados, sua pontuação é verificada para saber se ele quebrou algum recorde e então, a lista de jogos é atualizada.
    @Override
    public void salvarJogo(Jogo jogo) {
        jogosDAO.inserirJogo(jogo);
        verificaPontuacao(jogo.getPontuacao());
        carregarJogos();
    }
}