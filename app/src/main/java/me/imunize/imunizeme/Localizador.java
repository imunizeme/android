package me.imunize.imunizeme;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import me.imunize.imunizeme.dto.Clinicas;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Décio Montanhani on 23/09/2017.
 */
public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient client;
    private final GoogleMap mapa;
    Context context;
    private final Activity activity;

    public Localizador(Context context, GoogleMap mapa, Activity activity) {
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();


        client.connect();
        this.activity = activity;
        this.context = context;
        this.mapa = mapa;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(100);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 788);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 789);

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(coordenada);

        UsuarioService usuarioService = ServiceGenerator.createService();
        SPHelper spHelper = new SPHelper(context);

        Call<List<Clinicas>> call = usuarioService.pegarClinicas(spHelper.pegaToken(), location.getLatitude(), location.getLongitude());
        call.enqueue(new Callback<List<Clinicas>>() {
            @Override
            public void onResponse(Call<List<Clinicas>> call, Response<List<Clinicas>> response) {

                if(response.isSuccessful()){

                    List<Clinicas> lista = response.body();
                    inserirMarks(lista);

                }
            }

            @Override
            public void onFailure(Call<List<Clinicas>> call, Throwable t) {

            }
        });

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle);
        mapa.addMarker(new MarkerOptions().title("Localização Atual").position(coordenada).icon(icon));
        mapa.moveCamera(cameraUpdate);
    }

    private void inserirMarks(List<Clinicas> lista) {

        MarkerOptions marcador;
        BitmapDescriptor star = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_star_10);

        for (Clinicas clinica: lista
             ) {

            marcador = new MarkerOptions();
            marcador.title(clinica.getNome());
            marcador.snippet(clinica.toString());
            LatLng xy = new LatLng(clinica.getLat(), clinica.getLong());
            marcador.position(xy);
            if(clinica.isPatrocinado()){
                marcador.icon(star);
            }
            mapa.addMarker(marcador);

        }
    }


}













