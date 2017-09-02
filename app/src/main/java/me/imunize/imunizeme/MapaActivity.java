package me.imunize.imunizeme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapaActivity extends AppCompatActivity {

    @BindView(R.id.mapa_fragment)
    FrameLayout frameMapa;
    private FragmentManager manager;
    private FragmentTransaction tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ButterKnife.bind(this);

        manager = getSupportFragmentManager();
        tx = manager.beginTransaction();
        tx.replace(R.id.mapa_fragment, new MapaFragment());
        //tx.addToBackStack(null);
        tx.commit();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
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
