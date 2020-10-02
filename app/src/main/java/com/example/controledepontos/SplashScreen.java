package com.example.controledepontos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

//Essa classe é responsável por exibir uma tela de abertura para o aplicativo.
//O atributo dessa classe é o tempo que a tela será exibida para o usuário em milisegundos.
public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        //Esse método exibe a tela pelo tempo determinado e após isso, carrega a tela seguinte.
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Mostra a tela de abertura até o tempo estipulado acabar e em segudida,
                //abre a tela inicial do app
                Intent StartIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(StartIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}