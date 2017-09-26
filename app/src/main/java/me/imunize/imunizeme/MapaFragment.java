package me.imunize.imunizeme;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Sr. Décio Montanhani on 14/08/2017.
 */
@RuntimePermissions
public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getActivity().setTitle("Postos de Saúde");

        getMapAsync(this);

    }


    @Override
    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void onMapReady(GoogleMap googleMap) {


        LatLng fiap = pegaCoordenadaDoEndereco("Avenida Lins de Vasconcelos, 1222 - São Paulo");
        LatLng gui = pegaCoordenadaDoEndereco("Rua Antonio Tavares, 300 - São Paulo");


        if (fiap != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(fiap, 17);
            googleMap.moveCamera(update);
        }

        if(gui != null) {

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_star_10);
            MarkerOptions marcador = new MarkerOptions();

            marcador.position(gui);
            marcador.title("Clínica Particular");
            marcador.snippet("www.clinicaparticular.com.br");
            marcador.icon(icon);
            googleMap.addMarker(marcador);
        }

        new Localizador(getContext(), googleMap, getActivity());

    }


    private LatLng pegaCoordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

