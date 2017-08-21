package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dao.UsuarioDAO;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_email) EditText edtEmail;
    @BindView(R.id.signup_nomeCompleto) EditText edtNomeCompleto;
    @BindView(R.id.signup_senha) EditText edtSenha;
    @BindView(R.id.signup_cpf) EditText edtCpf;
    @BindView(R.id.signup_btCadastrar) TextView btCadastrar;
    @BindView(R.id.signup_layout_campos) LinearLayout layoutCampos;
    @BindView(R.id.signup_layout_progress) RelativeLayout layoutProgress;
    private UsuarioService usuarioService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        usuarioService = ServiceGenerator.createService();

    }

    @OnClick(R.id.signup_btCadastrar)
    public void cadastrarUsuario(){

        String email = edtEmail.getText().toString();
        String nomeCompleto = edtNomeCompleto.getText().toString();
        String senha = edtSenha.getText().toString();
        String cpf = edtCpf.getText().toString();

        Usuario usuario = new Usuario(nomeCompleto, email, cpf,senha);

        Log.i("Senha com hash: ", usuario.getHashPassword());
        Call<Void> call =  usuarioService.cadastrarUsuario(usuario.getCpf_cnpj(), usuario.getHashPassword());

        layoutCampos.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(SignUpActivity.this, "Erro ao fazer o cadastro. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    layoutCampos.setVisibility(View.VISIBLE);
                    layoutProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Erro ao fazer o cadastro. Tente novamente mais tarde", Toast.LENGTH_LONG);
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
