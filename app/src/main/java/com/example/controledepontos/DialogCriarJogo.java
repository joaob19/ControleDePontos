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

public class DialogCriarJogo extends AppCompatDialogFragment {
    private DialogCriarJogo.DialogCriarJogoListener listener;
    private EditText txtTituloJogo,txtPontuacaoJogo;


    public Dialog onCreateDialog(Bundle var1) {
        Builder builder = new Builder(this.getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_criar_jogo_daialog, null);

        txtTituloJogo = view.findViewById(R.id.txt_titulo_jogo);
        txtPontuacaoJogo = view.findViewById(R.id.txt_pontuacao_jogo);

        builder.setView(view).setTitle("Adicionar novo jogo").setNegativeButton("CANCELAR", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Adicionar", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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



    public interface DialogCriarJogoListener {
        void salvarJogo(Jogo jogo);
    }
}
