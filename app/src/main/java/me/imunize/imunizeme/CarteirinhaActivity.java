package me.imunize.imunizeme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imunize.imunizeme.dto.TokenDTO;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarteirinhaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SliderLayout mDemoSlider;
    public static Context context;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TextView txtNome;
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteirinha);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Home");

        context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Log.i("Quantidade de header: ", String.valueOf(navigationView.getHeaderCount()));

        View headerView = navigationView.getHeaderView(0);

        txtEmail = headerView.findViewById(R.id.menu_header_email);
        txtNome = headerView.findViewById(R.id.menu_header_nome);

        SPHelper spHelper = new SPHelper(this);
        txtNome.setText(spHelper.pegaNome());
        txtEmail.setText(spHelper.pegaEmail());

        exibeSlider();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token =  preferences.getString("tokenFirebase", null);

        if(spHelper.pegaFirebaseToken() == null) {
            spHelper.gravaFirebaseToken(FirebaseInstanceId.getInstance().getToken());
            UsuarioService usuarioService = ServiceGenerator.createService();
            Call<Void> call = usuarioService.enviarToken(spHelper.pegaToken(), new TokenDTO(spHelper.pegaFirebaseToken()));
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Log.i("Token Firebase:", "Enviado!");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }

    }

    protected void atualizaMenu(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        txtEmail = headerView.findViewById(R.id.menu_header_email);
        txtNome = headerView.findViewById(R.id.menu_header_nome);

        SPHelper spHelper = new SPHelper(this);
        txtNome.setText(spHelper.pegaNome());
        txtEmail.setText(spHelper.pegaEmail());

    }



    @Override
    protected void onResume() {
        super.onResume();

        atualizaMenu();
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
