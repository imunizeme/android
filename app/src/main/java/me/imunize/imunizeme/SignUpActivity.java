package me.imunize.imunizeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_email) EditText edtEmail;
    @BindView(R.id.signup_nomeCompleto) EditText edtNomeCompleto;
    @BindView(R.id.signup_senha) EditText edtSenha;
    @BindView(R.id.signup_cpf) EditText edtCpf;
    @BindView(R.id.signup_btCadastrar) TextView btCadastrar;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("127.0.0.1:8000/imunizeme/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usuarioService = retrofit.create(UsuarioService.class);

    }

    @OnClick(R.id.signup_btCadastrar)
    public void cadastrarUsuario(){

        String email = edtEmail.getText().toString();
        String nomeCompleto = edtNomeCompleto.getText().toString();
        String senha = edtSenha.getText().toString();
        String cpf = edtCpf.getText().toString();

        Usuario usuario = new Usuario(nomeCompleto, email, cpf,senha);

        usuarioService.cadastrarUsuario(usuario);

        Toast.makeText(this,"Estamos em manutenção, tente mais tarde", Toast.LENGTH_SHORT).show();

        //CadastrarUsuarioTask task = new CadastrarUsuarioTask();

    }
/*
    private class CadastrarUsuarioTask extends AsyncTask<String, Void, String>{

        private ProgressDialog progress;

        protected void onPreExecute(){
            progress = ProgressDialog.show(SignUpActivity.this, "Aguarde", "Efetuando o Cadastro...");
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL("http://viacep.com.br/ws/" + strings[0] + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ( (line = bufferedReader.readLine()) != null){
                    result.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        protected void onPostExecute(String result){
            progress.dismiss();



        }

    }
    */
}
