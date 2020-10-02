package com.example.controledepontos;


import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//Está classe é responsável por abrir a janela para adicionar um novo jogo.

public class DialogCriarJogo extends AppCompatDialogFragment {
    private DialogCriarJogo.DialogCriarJogoListener listener;
    private EditText txtTituloJogo,txtPontuacaoJogo;


    //este método é responsavel por executar outros métodos e implementar a janela, lhe dando um titulo, uma mensagem e implementando a ação de seus botões.
    public Dialog onCreateDialog(Bundle var1) {
        Builder builder = new Builder(this.getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_criar_jogo_daialog, null);

        txtTituloJogo = view.findViewById(R.id.txt_titulo_jogo);
        txtPontuacaoJogo = view.findViewById(R.id.txt_pontuacao_jogo);

        //Este é o construtor da janela e é aqui onde serão informaos seus atributos.
        builder.setView(view).setTitle("Adicionar novo jogo").setNegativeButton("CANCELAR", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Adicionar", new OnClickListener() {
            //Este método é responsável por executar outros métodos assim que o botão adicionar for clicado
            public void onClick(DialogInterface dialog, int which) {
                //Aqui ocorre uma verificação para saber se o usuário não deixou nenhum campo vazio.
                //Caso o usuário tenha preenchido corretamente, será criado um novo objeto Jogo que será prenchido com todos os dados necessários.
                //Após o preenchimento das informações do jogo, ele será retornado para a classe jogos e então será salvo.
                if((txtTituloJogo.getText().toString().length()>0)&&(txtPontuacaoJogo.getText().toString().length()>0)){
                    Jogo jogo = new Jogo();
                    int pontuacao = Integer.parseInt(txtPontuacaoJogo.getText().toString());
                    String titulo = txtTituloJogo.getText().toString();

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String data = df.format(c);

                    jogo.setTitulo(titulo);
                    jogo.setData(data);
                    jogo.setPontuacao(pontuacao);

                    listener.salvarJogo(jogo);
                }
                else{
                    Toast.makeText(getActivity(), "Você deve preencher todos os campos para poder salvar um jogo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    //Este método é responsável por verificar se o usuário implementou a interface DialogCriarJogoListener na classe desejada.
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (DialogCriarJogo.DialogCriarJogoListener)context;
        } catch (ClassCastException var3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.toString());
            stringBuilder.append("deve implementar DialogCriarJogo");
            throw new ClassCastException(stringBuilder.toString());
        }
    }

    //Interface que fica ouvindo até que o botão "Adicionar" seja clicado e com isso retorna um objeto jogo para classe em que a janela foi aberta.
    public interface DialogCriarJogoListener {
        void salvarJogo(Jogo jogo);
    }
}
