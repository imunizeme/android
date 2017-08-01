package me.imunize.imunizeme;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail, edtNomeCompleto, edtSenha, edtCpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void voltarTelaLogin(){

    }

    public void cadastrarUsuario(){

        edtEmail = (EditText) findViewById(R.id.email);
        edtNomeCompleto = (EditText) findViewById(R.id.nomeCompleto);
        edtSenha = (EditText) findViewById(R.id.senha);
        edtCpf = (EditText) findViewById(R.id.cpf);

        String email = edtEmail.getText().toString();
        String nomeCompleto = edtNomeCompleto.getText().toString();
        String senha = edtSenha.getText().toString();
        String cpf = edtCpf.getText().toString();

        CadastrarUsuarioTask task = new CadastrarUsuarioTask();


    }

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
}
