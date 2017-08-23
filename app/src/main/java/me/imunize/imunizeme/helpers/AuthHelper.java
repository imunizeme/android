package me.imunize.imunizeme.helpers;

import android.util.Base64;
import android.util.Log;

import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sr. Décio Montanhani on 21/08/2017.
 */

public class AuthHelper {

    private UsuarioService usuarioService;
    public static RespostaAutenticacao autenticacao;

    public AuthHelper(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public RespostaAutenticacao autenticar(String cpf, String senha){

        String auth = encriptationValue(cpf, senha);
        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);
        //final RespostaAutenticacao[] respostaAutenticacao = new RespostaAutenticacao[1];

        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {
                if(response.isSuccessful()){
                   autenticacao = response.body();
                    Log.i("Login ", "Auth Realizado com sucesso");
                }else{
                    Log.e("Login ", "Falha na requisição");
                    autenticacao = null;
                }
            }

            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {
                Log.e("Login ", "Falha na requisição, on Failure");
                autenticacao = null;
            }
        });

        return autenticacao;
    }

    public static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;


        return "Basic " + Base64.encodeToString(info.getBytes(), Base64.NO_WRAP);
    }


}
