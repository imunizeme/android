package me.imunize.imunizeme;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class VacinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina);

        setTitle("Adicionar Vacina");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> vacinas = new ArrayList<>();
        vacinas.add("BCG");
        vacinas.add("Influenza (Adulto)");
        vacinas.add("Hepatite B");
        vacinas.add("Tríplice Bacteriana (Difteria, Coqueluxe e Tétano) [Infantil]");
        vacinas.add("Haemophilus (Hib)");
        vacinas.add("Pólio");
        vacinas.add("Rotavirus Pentavalente");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vacinas);
        spinner.setAdapter(adapter);



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
