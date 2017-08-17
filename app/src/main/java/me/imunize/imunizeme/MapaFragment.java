package me.imunize.imunizeme;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sr. Décio Montanhani on 14/08/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng fiap = pegaCoordenadaDoEndereco("Avenida Lins de Vasconcelos, 1222 - São Paulo");
        LatLng gui = pegaCoordenadaDoEndereco("Rua Antonio Tavares, 300 - São Paulo");
        if (fiap != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(fiap, 17);
            googleMap.moveCamera(update);
        }

        MarkerOptions marcador = new MarkerOptions();
        marcador.position(fiap);
        marcador.title("FIAP - Faculdade de Informática e Administração Paulista");
        marcador.snippet("O meu app é foda, me chupem!");
        googleMap.addMarker(marcador);

        marcador.position(gui);
        marcador.title("Casa do Gui");
        marcador.snippet("Parabéns pelo emprego");
        googleMap.addMarker(marcador);

        /*AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()) {
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenada != null) {

            }
        }
        alunoDAO.close();


        */
        //new Localizador(getContext(), googleMap, getActivity());
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
