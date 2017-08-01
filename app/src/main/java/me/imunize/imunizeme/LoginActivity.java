package me.imunize.imunizeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText edtCpf, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(){
    /*
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
    */
    }

    private static String encriptationValue(String cpf, String senha){

        //senha = DigestUtils.sha1(senha).toString();

        String info = cpf + ":" + senha;

        return Base64.encodeBase64String(info.getBytes());
    }

}
