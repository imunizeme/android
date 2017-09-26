package me.imunize.imunizeme.service;

import java.util.List;
import java.util.Map;

import me.imunize.imunizeme.dto.AlterarDadosDTO;
import me.imunize.imunizeme.dto.AlterarSenhaDTO;
import me.imunize.imunizeme.dto.Clinicas;
import me.imunize.imunizeme.dto.Profile;
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.dto.TokenDTO;
import me.imunize.imunizeme.dto.VacinasDTO;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.dto.UsuarioCadastro;
import me.imunize.imunizeme.models.Vacina;
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

    @POST("api/imunizeme/public/carteirinha_vacina")
    Call<Void> cadastrarVacinaTomada(@Header("Authorization") String token, @Body VacinasDTO vacinasDTO);

    @PUT("api/imunizeme/public/profile")
    Call<Void> alterarProfile(@Header("Authorization") String token, @Query("user_id") int idUsuario, @Body AlterarDadosDTO dados);

    @GET("api/_QUERIES/vacinas/getchild")
    Call<List<Vacina>> pegarVacinasCrianca(@Header("Authorization") String token, @Query("user_id") int idUsuario);

    @GET("api/_QUERIES/vacinas/getadult")
    Call<List<Vacina>> pegarVacinasAdulto(@Header("Authorization") String token, @Query("user_id") int idUsuario);

    @GET("api/_QUERIES/nearby/list")
    Call<List<Clinicas>> pegarClinicas(@Header("Authorization") String token, @Query("lat") double lat, @Query("lng") double lng);

    @PUT("api/imunizeme/public/users")
    Call<Void> alterarSenha(@Header("Authorization") String token, @Query("cpf_cnpj") String cpf, @Body AlterarSenhaDTO senha);

    @GET("api/imunizeme/public/profile?_join=inner:users:profile.user_id:$eq:users.id")
    Call<List<Usuario>> pegarProfile(@Header("Authorization") String token, @Query("users.id") int userId);

    @GET("api/imunizeme/public/users")
     Call<Usuario> pegarUsuario();

    @POST("auth/auth")
    Call<RespostaAutenticacao> autenticarUsuario(@Header("Authorization") String authValue);

    @POST("api/imunizeme/public/notification_clients")
    Call<Void> enviarToken(@Header("Authorization") String auth, @Body TokenDTO clientId);

}
