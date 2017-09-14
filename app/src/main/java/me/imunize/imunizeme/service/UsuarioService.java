package me.imunize.imunizeme.service;

import java.util.List;
import java.util.Map;

import me.imunize.imunizeme.dto.Profile;
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.dto.UsuarioCadastro;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sr. Décio Montanhani on 16/08/2017.
 */

public interface UsuarioService {

    @POST("api/imunizeme/public/users")
    Call<Usuario> cadastrarUsuario(@HeaderMap Map<String, String> map, @Body UsuarioCadastro user);

    @POST("api/imunizeme/public/profile")
    Call<Void> cadastrarProfile(@Header("Authorization") String authValue, @Body Profile profile);

    //URL com JOIN
    //https://imunize.me/api/imunizeme/public/profile?_join=inner:users:profile.user_id:$eq:users.id&users.id=1

    @FormUrlEncoded
    @PUT("api/imunizeme/public/users")
    Call<Void> alterarSenha(@Header("Authorization") String token, @Query("cpf_cnpj") String cpf, @Field("password") String senha);

    @GET("api/imunizeme/public/profile?_join=inner:users:profile.user_id:$eq:users.id")
    Call<List<Usuario>> pegarProfile(@Header("Authorization") String token, @Query("users.id") int userId);

    @GET("api/imunizeme/public/users")
     Call<Usuario> pegarUsuario();

    @POST("auth/auth")
    Call<RespostaAutenticacao> autenticarUsuario(@Header("Authorization") String authValue);

    @FormUrlEncoded
    @POST("api/imunizeme/public/notification_clients")
    Call<Void> enviarToken(@Header("Authorization") String auth, @Field("client_id") String clientId);

}
