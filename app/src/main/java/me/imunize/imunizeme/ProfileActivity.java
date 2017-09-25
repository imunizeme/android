package me.imunize.imunizeme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.helpers.Mask;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    String token;
    Usuario user;
    UsuarioService usuarioService;
    @BindView(R.id.profile_txt_nome)
    EditText edtNome;
    @BindView(R.id.profile_txt_cpf)
    EditText edtCpf;
    @BindView(R.id.profile_txt_aniversario)
    EditText edtAniversario;
    @BindView(R.id.profile_txt_email)
    EditText edtEmail;
    @BindView(R.id.profile_layout_progress)
    LinearLayout progressBar;
    @BindView(R.id.profile_conteudo)
    LinearLayout conteudoProfile;
    public static Activity fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTitle("Perfil - Imunize.me");

        fa = this;

        edtCpf.addTextChangedListener(Mask.insert("###.###.###-##", edtCpf));
        usuarioService = ServiceGenerator.createService();

        SPHelper spHelper = new SPHelper(this);
        token = spHelper.pegaToken();
        //token = "Bearer " + token;

/*
        Usuario usuario2 = new Usuario("Décio", "deciomontanhani@gmail.com", "45196631801", "senha");
        usuario2.setAniversario("27/08/1996");

        preencheLabels(usuario2);
*/


        if(token != null){
            pegarProfile(token, spHelper.pegaIdUsuario());
        }else{
            Toast.makeText(this, "Não está gravando o Token", Toast.LENGTH_SHORT).show();
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void exibeConteudo(){

        progressBar.setVisibility(View.GONE);
        conteudoProfile.setVisibility(View.VISIBLE);

    }



    private void pegarProfile(String token, int id) {
        Call<List<Usuario>> call = usuarioService.pegarProfile(token, id);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                if(response.isSuccessful()){
                    Usuario usuario = response.body().get(0);
                    //Toast.makeText(ProfileActivity.this, usuario.getName(), Toast.LENGTH_SHORT).show();
                    preencheLabels(usuario);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro na aplicação", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.profile_bt_alterar_dados)
    protected void alterarDados(){

        Intent vaiAlterarDados = new Intent(this, AlterarDadosActivity.class);
        vaiAlterarDados.putExtra("usuario", user);
        startActivity(vaiAlterarDados);
        //finish();

    }


    @OnClick(R.id.profile_bt_alterar_senha)
    protected void alteraSenha(){

        Intent vaiAlterarSenha = new Intent(this, AlterarSenha.class);
        vaiAlterarSenha.putExtra("usuario", user);
        startActivity(vaiAlterarSenha);

    }


    private void preencheLabels(Usuario usuario){

        if(usuario != null){
            exibeConteudo();
            user = usuario;
            edtNome.setText(usuario.getName());
            edtCpf.setText(usuario.getCpfCnpj());
            edtEmail.setText(usuario.getEmail());
            edtAniversario.setText(usuario.getAniversario());
        }

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
