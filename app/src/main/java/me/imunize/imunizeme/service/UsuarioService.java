package me.imunize.imunizeme.service;

import me.imunize.imunizeme.models.RespostaAutenticacao;
import me.imunize.imunizeme.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Sr. Décio Montanhani on 16/08/2017.
 */

public interface UsuarioService {

    @FormUrlEncoded
    @POST("api/imunizeme/public/users")
    Call<Void> cadastrarUsuario(@Field("cpf_cnpj") String cpf, @Field("password") String password);

    @GET("api/imunizeme/public/users")
     Call<Usuario> pegarUsuario();

    @FormUrlEncoded
    @POST("auth/auth")
    Call<RespostaAutenticacao> autenticarUsuario(@Header("Authorization") String authValue, @Field("cpf_cnpj") String cpf, @Field("password") String senha);

}
