package me.imunize.imunizeme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.dto.UsuarioCadastro;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_email) EditText edtEmail;
    @BindView(R.id.signup_nomeCompleto) EditText edtNomeCompleto;
    @BindView(R.id.signup_senha) EditText edtSenha;
    @BindView(R.id.signup_cpf) EditText edtCpf;
    @BindView(R.id.signup_btCadastrar) TextView btCadastrar;
    @BindView(R.id.signup_layout_campos) LinearLayout layoutCampos;
    @BindView(R.id.signup_layout_progress) RelativeLayout layoutProgress;
    private UsuarioService usuarioService;
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        usuarioService = ServiceGenerator.createService();

    }

    @OnClick(R.id.signup_btCadastrar)
    public void cadastrarUsuario(){

        final String email = edtEmail.getText().toString();
        final String nomeCompleto = edtNomeCompleto.getText().toString();
        final String senha = edtSenha.getText().toString();
        final String cpf = edtCpf.getText().toString();

        String auth = LoginActivity.encriptationValue("45196631801", "imunizeme");
        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);




        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {
                RespostaAutenticacao resposta = response.body();
                Log.i("Body ", response.body().toString());

                if(response.isSuccessful()){
                    token =  "Bearer " + resposta.getToken();
                    Log.i("Token -> ", token);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                    SharedPreferences.Editor ed = preferences.edit();
                    ed.putString("token", token);
                    ed.commit();

                    Toast.makeText(SignUpActivity.this, "Autenticou com Administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
            }
        });


        Usuario usuario = new Usuario(nomeCompleto, email, cpf,senha);

        Log.i("Senha com hash: ", usuario.getHashPassword());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", preferences.getString("token", null));

        Call<Void> call2 =  usuarioService.cadastrarUsuario(map, new UsuarioCadastro(usuario.getCpf_cnpj(), usuario.getHashPassword()));

        layoutCampos.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.VISIBLE);

        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent vaiPraHome = new Intent(SignUpActivity.this, CarteirinhaActivity.class);
                    startActivity(vaiPraHome);
                    finish();

                }else{
                    Log.i("Body: ", response.raw().toString());
                    Toast.makeText(SignUpActivity.this, "Erro ao fazer o cadastro. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    layoutCampos.setVisibility(View.VISIBLE);
                    layoutProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("onFailure -> ", t.getMessage());
                layoutCampos.setVisibility(View.VISIBLE);
                layoutProgress.setVisibility(View.GONE);
            }
        });


        /*UsuarioDAO dao = new UsuarioDAO(this);

        if(dao.existeCPF(cpf)){
            Toast.makeText(this,"Esse CPF já existe na nossa base de dados. Tente outro.", Toast.LENGTH_SHORT).show();
        }else{
            dao.insere(usuario);
            Intent vaiPraHome = new Intent(this, CarteirinhaActivity.class);
            startActivity(vaiPraHome);
            finish();
        }*/



        //usuarioService.cadastrarUsuario(usuario);

        //Toast.makeText(this,"Estamos em manutenção, tente mais tarde", Toast.LENGTH_SHORT).show();

        //CadastrarUsuarioTask task = new CadastrarUsuarioTask();

    }
}
