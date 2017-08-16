package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.models.Usuario;

public class LoginActivity extends AppCompatActivity {

    //private EditText edtCpf, edtSenha;
    @BindView(R.id.login_btcadastro) TextView btCadastro;
    @BindView(R.id.login_btEntrar) TextView btEntrar;
    @BindView(R.id.login_cpf) EditText edtCPF;
    @BindView(R.id.login_senha) EditText edtSenha;

    private String cpf, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.login_btEntrar)
    protected void entrar() {

        cpf = edtCPF.getText().toString();
        senha = edtSenha.getText().toString();

        Usuario usuario = new Usuario(cpf, senha);

        //new EnviaLoginTask(LoginActivity.this).execute(usuario);
        //UsuarioConverter converter = new UsuarioConverter();


        if(edtCPF.getText().toString().equals("123") && edtSenha.getText().toString().equals("imunizeme")){
            Intent intentCarteirinha = new Intent(LoginActivity.this, CarteirinhaActivity.class);
            startActivity(intentCarteirinha);
            finish();
        }else{

            Toast.makeText(LoginActivity.this,"Login e/ou senha errados, tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.login_btcadastro)
    protected void cadastrar() {
        Intent intentCadastro = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentCadastro);
    }

    /*
    public void login(){

        edtCpf = (EditText) findViewById(R.id.usrusr);
        edtSenha = (EditText) findViewById(R.id.passwrd);

        String cpf = edtCpf.getText().toString();
        String senha = edtSenha.getText().toString();

        String authValue = encriptationValue(cpf, senha);

        URL url = null;
        try {
            url = new URL("201.95.56.222:4000/auth"); //Colocar o IP da minha máquina para rodar no celular
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", authValue);
            if(connection.getResponseCode() == 200){
                Toast.makeText(this, connection.getResponseMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Message", connection.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return result.toString();

    }*/
/*
    private static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;

        return "Basic " + Base64.encodeBase64String(info.getBytes());
    }
        */
}
