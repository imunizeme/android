package me.imunize.imunizeme;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dto.Profile;
import me.imunize.imunizeme.dto.RespostaAutenticacao;
import me.imunize.imunizeme.dto.Sexo;
import me.imunize.imunizeme.dto.SexoBO;
import me.imunize.imunizeme.helpers.Mask;
import me.imunize.imunizeme.helpers.Validator;
import me.imunize.imunizeme.models.Usuario;
import me.imunize.imunizeme.dto.UsuarioCadastro;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_email) EditText edtEmail;
    @BindView(R.id.signup_nomeCompleto) EditText edtNomeCompleto;
    @BindView(R.id.signup_senha) EditText edtSenha;
    @BindView(R.id.signup_cpf) EditText edtCpf;
    @BindView(R.id.signup_btCadastrar) TextView btCadastrar;
    @BindView(R.id.signup_layout_campos) LinearLayout layoutCampos;
    @BindView(R.id.signup_layout_progress) RelativeLayout layoutProgress;
    @BindView(R.id.signup_data) EditText edtAniversario;
    @BindView(R.id.signup_spn_sexo) Spinner spnSexo;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));
    private UsuarioService usuarioService;
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        List<Sexo> lista = new SexoBO().list();

        spnSexo.setAdapter(new ArrayAdapter<Sexo>(this, android.R.layout.simple_spinner_item, lista));

        edtCpf.addTextChangedListener(Mask.insert("###.###.###-##", edtCpf));
        usuarioService = ServiceGenerator.createService();

    }

    @OnClick(R.id.signup_btCadastrar)
    public void cadastrarUsuario(){

        if(Validator.validateNotNull(edtCpf, "Preencha o CPF") &&
                Validator.validateNotNull(edtSenha, "Preencha a Senha") &&
                Validator.validateNotNull(edtEmail, "Preencha o Email") &&
                Validator.validateNotNull(edtNomeCompleto, "Preencha o Nome") &&
                Validator.validateNotNull(edtAniversario, "Preencha o Aniversário") &&
                spnSexo.getSelectedItem() != null)
        {

            String email = edtEmail.getText().toString();
            String nomeCompleto = edtNomeCompleto.getText().toString();
            String senha = edtSenha.getText().toString();
            String cpf = Mask.unmask(edtCpf.getText().toString());
            String aniversario = edtAniversario.getText().toString();
            Sexo sexo = (Sexo) spnSexo.getSelectedItem();

            boolean emailValido = Validator.validateEmail(email);
            boolean cpfValido = Validator.validateCPF(cpf);

            if(!emailValido){
                edtEmail.setError("Email inválido");
                edtEmail.setFocusable(true);
                edtEmail.requestFocus();
            }else if(!cpfValido){
                edtCpf.setError("CPF inválido");
                edtCpf.setFocusable(true);
                edtCpf.requestFocus();
            } else{
                String auth = LoginActivity.encriptationValue("45196631801", "imunizeme");
                pegaToken(auth, email, nomeCompleto, senha, cpf, aniversario, sexo.getSexo());
            }
        }
    }

    @OnClick(R.id.signup_data)
    public void selecionarData(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date aniversario = calendar.getTime();
                edtAniversario.setText(format.format(aniversario));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    private void fazCadastro(final String email, final String nomeCompleto, final String senha, final String cpf, final String aniversario, final String sexo) {
        Usuario usuario = new Usuario(nomeCompleto, email, cpf,senha);

        Log.i("Senha com hash: ", usuario.getHashPassword());

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", preferences.getString("token", null));

        Call<Usuario> call2 =  usuarioService.cadastrarUsuario(map, new UsuarioCadastro(usuario.getCpfCnpj(), usuario.getHashPassword()));

        layoutCampos.setVisibility(View.GONE);
        layoutProgress.setVisibility(View.VISIBLE);

        call2.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.isSuccessful()){

                    Usuario usuario1 = response.body();

                    if (usuario1 != null) {
                        cadastraProfile(email, nomeCompleto, aniversario, usuario1.getId(), preferences.getString("token", null), sexo);

                        Toast.makeText(SignUpActivity.this, "Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();
                        Intent vaiPraLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(vaiPraLogin);
                        finish();
                    }

                }else{
                    Log.i("Body: ", response.raw().toString());
                    Toast.makeText(SignUpActivity.this, "Erro ao fazer o cadastro. Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    layoutCampos.setVisibility(View.VISIBLE);
                    layoutProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("onFailure -> ", t.getMessage());
                layoutCampos.setVisibility(View.VISIBLE);
                layoutProgress.setVisibility(View.GONE);
            }
        });
    }

    private void cadastraProfile(final String email, final String nomeCompleto, final String aniversario, final Long id, String token, final String sexo) {

        Call<Void> call = usuarioService.cadastrarProfile(token, new Profile(nomeCompleto, email, aniversario, id, sexo));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Profile Cadastrado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Erro ao inserir profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pegaToken(String auth, final String email, final String nomeCompleto, final String senha, final String cpf, final String aniversario, final String sexo) {

        Call<RespostaAutenticacao> call = usuarioService.autenticarUsuario(auth);

        call.enqueue(new Callback<RespostaAutenticacao>() {
            @Override
            public void onResponse(Call<RespostaAutenticacao> call, Response<RespostaAutenticacao> response) {
                RespostaAutenticacao resposta = response.body();
                Log.i("Body ", response.body().toString());

                if(response.isSuccessful()){
                    token =  "Bearer " + resposta.getToken();
                    Log.i("Token -> ", token);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                    SharedPreferences.Editor ed = preferences.edit();
                    ed.putString("token", token);
                    ed.commit();
                    fazCadastro(email, nomeCompleto, senha, cpf, aniversario, sexo);

                    //Toast.makeText(SignUpActivity.this, "Autenticou com Administrador", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<RespostaAutenticacao> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
