package me.imunize.imunizeme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.dto.UserInfo;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    SPHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        spHelper = new SPHelper(this);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(spHelper.estaLogado() == null || spHelper.estaLogado() == ""){
                    mostrarLogin();
                }else{
                    fazLogin(spHelper.estaLogado());
                }
            }
        }, 2000);

    }

    private void fazLogin(String auth) {
        UsuarioService usuarioService = ServiceGenerator.createService();

        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);
        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {

                RespostaAutenticacao resposta = response.body();

                if(response.isSuccessful()){

                    spHelper.gravaToken(resposta.getToken());
                    UserInfo userInfo = resposta.getUserInfo();
                    spHelper.gravaIdUsuario(userInfo.getId());

                    Intent vaiPraHome = new Intent(SplashScreenActivity.this, CarteirinhaActivity.class);
                    startActivity(vaiPraHome);
                    finish();
                }else if(response.code() > 309 && response.code() < 499){
                    Toast.makeText(SplashScreenActivity.this, "Login e/ou senha alterados no ultimo acesso. Faça Login novamente", Toast.LENGTH_SHORT).show();
                    mostrarLogin();
                }else{
                    Toast.makeText(SplashScreenActivity.this, "Com erro!", Toast.LENGTH_SHORT).show();
                    mostrarLogin();
                }

            }

            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {

            }
        });

    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
