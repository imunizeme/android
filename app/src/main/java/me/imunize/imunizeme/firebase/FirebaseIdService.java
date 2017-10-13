package me.imunize.imunizeme.firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import me.imunize.imunizeme.LoginActivity;
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.dto.TokenDTO;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sr. Décio Montanhani on 24/08/2017.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase Token: ", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        enviarTokenParaServidor(refreshedToken);
    }

    private void enviarTokenParaServidor(String refreshedToken) {

        UsuarioService usuarioService = ServiceGenerator.createService();
        String auth = LoginActivity.encriptationValue("45196631801", "imunizeme");

        autenticar(usuarioService, auth);
        callEnviarToken(refreshedToken, usuarioService);
    }

    private void callEnviarToken(final String refreshedToken, UsuarioService usuarioService) {

        final SPHelper helper = new SPHelper(this);
        String token =  helper.pegaToken();

        Call<Void> call = usuarioService.enviarToken(token, new TokenDTO(refreshedToken));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    helper.gravaFirebaseToken(refreshedToken);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void autenticar(UsuarioService usuarioService, String auth) {
        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);

        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {
                RespostaAutenticacao resposta = response.body();
                Log.i("Body ", response.body().toString());

                if(response.isSuccessful()){
                    String token =  "Bearer " + resposta.getToken();
                    Log.i("Token -> ", token);
                    SPHelper helper = new SPHelper(FirebaseIdService.this);
                    helper.gravaToken(token);
                }
            }

            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {

            }
        });
    }

}
