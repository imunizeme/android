package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.imunize.imunizeme.converter.UsuarioConverter;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.tasks.EnviaLoginTask;

public class LoginActivity extends AppCompatActivity {

    //private EditText edtCpf, edtSenha;
    private TextView btCadastro, btEntrar;
    private String cpf, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btCadastro = (TextView) findViewById(R.id.cadastro);

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCadastro = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentCadastro);
            }
        });

        btEntrar = (TextView) findViewById(R.id.login_btCadastro);
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edtCPF = (EditText) findViewById(R.id.login_cpf);
                EditText edtSenha = (EditText) findViewById(R.id.login_senha);

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
        });


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

        return Base64.encodeBase64String(info.getBytes());
    }
        */
}
