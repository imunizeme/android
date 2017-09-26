package me.imunize.imunizeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dto.AlterarDadosDTO;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.helpers.Validator;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlterarDadosActivity extends AppCompatActivity {


    @BindView(R.id.alterar_txt_nome)
    EditText edtNome;
    @BindView(R.id.alterar_txt_email)
    EditText edtEmail;
    @BindView(R.id.alterar_txt_aniversario)
    EditText edtAniversario;
    private Usuario usuario;
    private UsuarioService usuarioService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);
        ButterKnife.bind(this);

        usuarioService = ServiceGenerator.createService();
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        edtNome.setText(usuario.getName());
        edtAniversario.setText(usuario.getAniversario());
        edtEmail.setText(usuario.getEmail());

    }

    @OnClick(R.id.alterar_dados_bt_confirmar)
    protected void alterarDados(){

        if(Validator.validateNotNull(edtEmail, "Preencha o Email") &&
                Validator.validateNotNull(edtNome, "Preencha o Nome") &&
                Validator.validateNotNull(edtAniversario, "Preencha o Aniversário")) {


            final AlterarDadosDTO dados = new AlterarDadosDTO();

            dados.setName(edtNome.getText().toString());
            dados.setEmail(edtEmail.getText().toString());
            dados.setAniversario(edtAniversario.getText().toString());

            SPHelper spHelper = new SPHelper(this);
            int idUsuario = spHelper.pegaIdUsuario();

            Call<Void> call = usuarioService.alterarProfile(spHelper.pegaToken(), idUsuario, dados);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AlterarDadosActivity.this, "Dados alterados com sucesso!!", Toast.LENGTH_SHORT).show();
                        new SPHelper(AlterarDadosActivity.this).gravaPerfil(dados.getName(), dados.getEmail(), dados.getAniversario());

                        ProfileActivity.fa.finish();
                        Intent intent = new Intent(AlterarDadosActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (response.code() > 499) {
                        Toast.makeText(AlterarDadosActivity.this, "Erro no Servidor. Tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AlterarDadosActivity.this, "Fatal Error  " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AlterarDadosActivity.this, "Erro no Servidor. Tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
