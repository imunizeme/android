package me.imunize.imunizeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imunize.imunizeme.helpers.SPHelper;

public class CarteirinhaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SliderLayout mDemoSlider;
    @BindView(R.id.toolbar)
        Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteirinha);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        exibeSlider();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SPHelper spHelper = new SPHelper(this);

        if(spHelper.isFirstTime() && toolbar != null) {

            TapTargetSequence sequence = new TapTargetSequence(this).targets(

                TapTarget.forToolbarNavigationIcon(toolbar, "Navegar entre opções", "Selecione aqui pra acessar suas opções").cancelable(false)

            );

            sequence.start();
            spHelper.registrarAcesso();


        }


    }

    private void exibeSlider() {
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Cuide do seu coração",R.drawable.coracao);
        file_maps.put("Vá ao médico regularmente",R.drawable.medico);
        file_maps.put("Cuide da saúde",R.drawable.saude);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_perfil:
                Intent vaiPraProfile = new Intent(this, ProfileActivity.class);
                startActivity(vaiPraProfile);
                break;

            case R.id.nav_carteirinha:
                Intent vaiPraCaderneta = new Intent(this, CadernetaActivity.class);
                startActivity(vaiPraCaderneta);
                break;

            case R.id.nav_clinica_proxima:
                Intent intent = new Intent(this, MapaActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_sair:
                new SPHelper(this).fazLogoff();
                Intent vaiProLogin = new Intent(this, LoginActivity.class);
                startActivity(vaiProLogin);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
