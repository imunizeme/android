package me.imunize.imunizeme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Sr. Décio Montanhani on 17/08/2017.
 */

public class AdultoFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carteirinha_adulto, container, false);

        FloatingActionButton cadastrar = (FloatingActionButton) view.findViewById(R.id.adulto_nova_vacina);

        cadastrar.setImageResource(R.drawable.icon_seringa);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Estamos em manutenção. Tente mais tarde!", Toast.LENGTH_SHORT).show();
            }
        });

        ListView listaVacinas = (ListView) view.findViewById(R.id.adulto_lista_vacinas);
        String[] vacinas = {"Tríplice Viral - Dose 1/3", "Hepatite B - Dose 1/3", "Varicela - Dose 1/2"};
        listaVacinas.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, vacinas));


        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.adulto_swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"Atualizado!", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        });



        return view;

    }
}
