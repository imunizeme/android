package me.imunize.imunizeme;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarSenha extends AppCompatActivity {

    @BindView(R.id.senha_txt_nova_senha)
    EditText edtNovaSenha;
    @BindView(R.id.senha_txt_senha_atual)
    EditText edtSenhaAtual;
    @BindView(R.id.senha_txt_senha_again)
    EditText edtSenhaAgain;

    private Usuario usuario;
    private UsuarioService usuarioService;
    private SPHelper spHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        setTitle("Alterar Senha");
        spHelper = new SPHelper(this);
        usuarioService = ServiceGenerator.createService();

        ButterKnife.bind(this);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.senha_bt_alterar)
    protected void alteraSenha() {
        if(confereSenhaAtual()){
            if(comparaSenhasNovas()){

                String novaSenha = usuario.sha1(edtNovaSenha.getText().toString());
                Call<Void> call = usuarioService.alterarSenha(spHelper.pegaToken(), usuario.getCpfCnpj(), novaSenha);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AlterarSenha.this, "Sucesso ao alterar a Senha", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else{
                            Toast.makeText(AlterarSenha.this, "Falha ao carregar senha", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                        Toast.makeText(AlterarSenha.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                    }
                });


            }else{
                Toast.makeText(this, "As senhas Novas não estão iguais", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Senha antiga incorreta.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean confereSenhaAtual(){
        return usuario.getPassword().equals(usuario.sha1(edtSenhaAtual.getText().toString()));
    }

    private boolean comparaSenhasNovas(){
        return edtNovaSenha.getText().toString().equals(edtSenhaAgain.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }

        return true;

    }
}
