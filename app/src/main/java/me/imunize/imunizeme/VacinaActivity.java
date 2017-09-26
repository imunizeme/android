package me.imunize.imunizeme;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imunize.imunizeme.dto.VacinasDTO;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.models.Vacina;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacinaActivity extends AppCompatActivity {

    final ArrayList<String> vacinas = new ArrayList<>();
    UsuarioService usuarioService = ServiceGenerator.createService();
    private Spinner spinner;
    private EditText edtData;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina);

        setTitle("Adicionar Vacina");

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        spinner = (Spinner) findViewById(R.id.spinner);

        edtData = (EditText) findViewById(R.id.vacina_data);


        //Trazer Vacinas não tomadas

        SPHelper helper = new SPHelper(this);
        Call<List<Vacina>> call = usuarioService.pegarVacinasAdulto(helper.pegaToken(), helper.pegaIdUsuario());
        final Call<List<Vacina>> call2 = usuarioService.pegarVacinasCrianca(helper.pegaToken(), helper.pegaIdUsuario());

        call.enqueue(new Callback<List<Vacina>>() {
            @Override
            public void onResponse(Call<List<Vacina>> call, Response<List<Vacina>> response) {


                if(response.isSuccessful()){
                    List<Vacina> body = response.body();
                    if(body != null )
                        pegaVacinasCrianca(call2, body);

                }
            }

            @Override
            public void onFailure(Call<List<Vacina>> call, Throwable t) {

            }
        });


        //Separa as não tomadas e insere no Spinner



        //adicionaVacinas();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boolean reforco = false;
                int dose = 0;

                switch (i){
                    case 0:

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.vacina_cadastrar)
    protected void cadastrarVacina(){

        Vacina vacina = (Vacina) spinner.getSelectedItem();
        String data = edtData.getText().toString();

        SPHelper helper = new SPHelper(this);

        VacinasDTO vacinasDTO = new VacinasDTO(helper.pegaIdUsuario(), vacina.getId(), data);
        Call<Void> call = usuarioService.cadastrarVacinaTomada(helper.pegaToken(), vacinasDTO);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    Toast.makeText(VacinaActivity.this, "Vacina Cadastrada com Sucesso!!", Toast.LENGTH_SHORT).show();
                    CadernetaActivity.fa.finish();
                    Intent intent = new Intent(VacinaActivity.this, CadernetaActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }



    @OnClick(R.id.vacina_data)
    protected void selecionarData(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date aniversario = calendar.getTime();
                edtData.setText(format.format(aniversario));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }



    private void pegaVacinasCrianca(Call<List<Vacina>> call2, final List<Vacina> body) {

        call2.enqueue(new Callback<List<Vacina>>() {
            @Override
            public void onResponse(Call<List<Vacina>> call, Response<List<Vacina>> response) {
                if(response.isSuccessful()){
                    List<Vacina> resposta = response.body();
                    resposta.addAll(body);
                    colocaNoSpinner(resposta);

                }
            }

            @Override
            public void onFailure(Call<List<Vacina>> call, Throwable t) {

            }
        });

    }

    private void colocaNoSpinner(List<Vacina> resposta) {

        List<Vacina> excluidas = new ArrayList<>();
        for (Vacina vacina:
                resposta) {
            if(vacina.getIdCarteirinha() > 0){
                   excluidas.add(vacina);
            }
        }

        resposta.removeAll(excluidas);

        ArrayAdapter<Vacina> adapter = new ArrayAdapter<Vacina>(this, android.R.layout.simple_list_item_1, resposta);
        spinner.setAdapter(adapter);


    }

    private void adicionaVacinas() {
        vacinas.add("BCG");
        vacinas.add("Dupla Tipo Adulto (dT)");
        vacinas.add("Febre Amarela");
        vacinas.add("Hepatite B");
        vacinas.add("Pneumocócica 23-valente (Pn23)");
        vacinas.add("Tetravalente (DTP + Hib)");
        vacinas.add("Tríplice Bacteriana (DTP)");
        vacinas.add("Tríplice Viral (SCR)");
        vacinas.add("Vacina Meningocócica C (conjugada)");
        vacinas.add("Vacina Oral de Rotavírus Humano (VORH)");
        vacinas.add("Vacina Oral Poliomielite (VOP)");
        vacinas.add("Vacina Pneumocócica 10 (conjugada)");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
