package me.imunize.imunizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.helpers.Mask;
import me.imunize.imunizeme.helpers.Validator;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        edtCPF.addTextChangedListener(Mask.insert("###.###.###-##", edtCPF));

    }

    @OnClick(R.id.login_btEntrar)
    protected void entrar() {

        if(Validator.validateNotNull(edtCPF, "Preencha o CPF") &&
                Validator.validateNotNull(edtSenha, "Preencha a Senha") &&
                Validator.validateCPF(edtCPF.getText().toString())){

            cpf = Mask.unmask(edtCPF.getText().toString());
            senha = edtSenha.getText().toString();

            String auth = encriptationValue(cpf, senha);

            layoutCampos.setVisibility(View.GONE);
            layoutProgress.setVisibility(View.VISIBLE);

            fazLogin(auth);

        }
    }

    private void fazLogin(String auth) {
        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);

        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {

                RespostaAutenticacao resposta = response.body();

                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Com sucesso!", Toast.LENGTH_SHORT).show();
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
    }

    @OnClick(R.id.login_btcadastro)
    protected void cadastrar() {
        Intent intentCadastro = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentCadastro);
    }


    public static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;

        return "Basic " + Base64.encodeToString(info.getBytes(), Base64.NO_WRAP);
    }

}
