package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dao.UsuarioDAO;
import me.imunize.imunizeme.models.RespostaAutenticacao;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //private EditText edtCpf, edtSenha;
    @BindView(R.id.login_btcadastro) TextView btCadastro;
    @BindView(R.id.login_btEntrar) TextView btEntrar;
    @BindView(R.id.login_cpf) EditText edtCPF;
    @BindView(R.id.login_senha) EditText edtSenha;
    @BindView(R.id.login_layout_campos) LinearLayout layoutCampos;
    @BindView(R.id.login_layout_progress) RelativeLayout layoutProgress;

    private String cpf, senha;
    UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioService = ServiceGenerator.createService();

        ButterKnife.bind(this);

    }

    @OnClick(R.id.login_btEntrar)
    protected void entrar() {

        cpf = edtCPF.getText().toString();
        senha = edtSenha.getText().toString();

        String auth = encriptationValue(cpf, senha);

        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth, cpf, senha);

        layoutCampos.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {

                RespostaAutenticacao resposta = response.body();

                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Com sucesso!"+ resposta.getToken(), Toast.LENGTH_SHORT).show();
                    Intent vaiPraHome = new Intent(LoginActivity.this, CarteirinhaActivity.class);
                    startActivity(vaiPraHome);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login e/ou senha incorretos, tente Novamente.", Toast.LENGTH_SHORT).show();
                    layoutCampos.setVisibility(View.VISIBLE);
                    layoutProgress.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Com erro!", Toast.LENGTH_SHORT).show();
                layoutCampos.setVisibility(View.VISIBLE);
                layoutProgress.setVisibility(View.GONE);
            }
        });

        //new EnviaLoginTask(LoginActivity.this).execute(usuario);
        //UsuarioConverter converter = new UsuarioConverter();

        /*UsuarioDAO dao = new UsuarioDAO(this);
        Usuario login = dao.fazLogin(usuario);
        if(login != null){
            Intent vaiPraHome = new Intent(this, CarteirinhaActivity.class);
            startActivity(vaiPraHome);
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"Login e/ou senha errados, tente novamente.", Toast.LENGTH_SHORT).show();
        }*/

    }

    @OnClick(R.id.login_btcadastro)
    protected void cadastrar() {
        Intent intentCadastro = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentCadastro);
    }


    private static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;


        return "Basic " + Base64.encodeToString(info.getBytes(), Base64.NO_WRAP);
    }

}
