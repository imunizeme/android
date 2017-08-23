package me.imunize.imunizeme.service;

import java.util.Map;

import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.dto.UsuarioCadastro;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Sr. Décio Montanhani on 16/08/2017.
 */

public interface UsuarioService {

    @POST("api/imunizeme/public/users")
    Call<Void> cadastrarUsuario(@HeaderMap Map<String, String> map, @Body UsuarioCadastro user);

    @GET("api/imunizeme/public/users")
     Call<Usuario> pegarUsuario();

    @POST("auth/auth")
    Call<RespostaAutenticacao> autenticarUsuario(@Header("Authorization") String authValue);

}
