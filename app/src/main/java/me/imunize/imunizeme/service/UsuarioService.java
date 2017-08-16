package me.imunize.imunizeme.service;

import me.imunize.imunizeme.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Sr. Décio Montanhani on 16/08/2017.
 */

public interface UsuarioService {

    @POST("users")
    Call<Void> cadastrarUsuario(@Body Usuario usuario);

    @GET("users")
     Call<Usuario> pegarUsuario();

    @POST("auth")
    Call<Void> autenticarUsuario(@Header("Authorization") String authValue, @Field("cpf_cnpj") String cpf, @Field("password") String senha);

}
