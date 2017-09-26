package me.imunize.imunizeme;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.imunize.imunizeme.adapters.MenuItemClickListener;
import me.imunize.imunizeme.adapters.VacinaCriancaAdapter;
import me.imunize.imunizeme.dto.VacinasDTO;
import me.imunize.imunizeme.helpers.SPHelper;
import me.imunize.imunizeme.list.HeaderItem;
import me.imunize.imunizeme.list.ListItem;
import me.imunize.imunizeme.list.VacinaItem;
import me.imunize.imunizeme.models.Vacina;
import me.imunize.imunizeme.service.ServiceGenerator;
import me.imunize.imunizeme.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sr. Décio Montanhani on 17/08/2017.
 */

public class CriancaFragment extends android.support.v4.app.Fragment {

    @NonNull
    private List<ListItem> items = new ArrayList<>();
    private List<Vacina> lista = new ArrayList<>();

    RecyclerView carteirinha;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_carteirinha_crianca, container, false);
        final FragmentActivity c = getActivity();
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.crianca_swipe);

        carteirinha = (RecyclerView) view.findViewById(R.id.crianca_lista_vacinas);
        carteirinha.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Início da Requisição da Lista

        UsuarioService usuarioService = ServiceGenerator.createService();

        SPHelper helper = new SPHelper(getContext());

        Call<List<Vacina>> call = usuarioService.pegarVacinasCrianca(helper.pegaToken(), helper.pegaIdUsuario());

        call.enqueue(new Callback<List<Vacina>>() {
            @Override
            public void onResponse(Call<List<Vacina>> call, Response<List<Vacina>> response) {

                if(response.isSuccessful()){

                    List<Vacina> vacinas = response.body();

                    for (Vacina vacina :
                            vacinas) {
                        if (vacina.getIdCarteirinha() > 0) {
                            vacina.setTomou(1);
                            lista.add(vacina);
                        }else{
                            vacina.setTomou(0);
                            lista.add(vacina);
                        }
                    }
                    lista = ordenaVacinas(lista);
                    geraItensOrdenados(lista);
                }

            }

            @Override
            public void onFailure(Call<List<Vacina>> call, Throwable t) {

            }
        });

        //Fim do código da lista

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        VacinaCriancaAdapter adapter = new VacinaCriancaAdapter(items);

        carteirinha.setAdapter(adapter);

        adapter.setMenuItemClickListener(new MenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                VacinaItem item = (VacinaItem) items.get(position);
                Vacina vacina = item.getVacina();
                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Informações");
                dialog.setContentView(R.layout.info_vacinas);

                TextView textoDoencas = (TextView) dialog.findViewById(R.id.dialog_edt_doencas);
                textoDoencas.setText(vacina.getDoencasEvitadas());
                TextView textoObservacoes = (TextView) dialog.findViewById(R.id.dialog_edt_Observacoes);
                textoObservacoes.setText(vacina.getObservacoes());
                dialog.show();
            }
        });

        //carteirinha.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, vacinas));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Atualizado!", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        });


        return view;

    }

    private void geraItensOrdenados(List<Vacina> lista) {
        Map<Integer, List<Vacina>> vacinas = toMap(lista);

        for (int tomou : vacinas.keySet()) {
            HeaderItem header = new HeaderItem(tomou);
            items.add(header);
            for (Vacina vacina : vacinas.get(tomou)) {
                VacinaItem item = new VacinaItem(vacina);
                items.add(item);
            }
        }
    }

    private List<Vacina> ordenaVacinas(List<Vacina> vacinas) {
        Collections.sort(vacinas, new Comparator<Vacina>() {
            @Override
            public int compare(Vacina vacina, Vacina t1) {
                if(vacina.getTomou() > t1.getTomou()){
                    return -1;
                }else if(vacina.getTomou() < t1.getTomou()){
                    return +1;
                }else{

                    return 0;
                }
            }
        });

        return vacinas;
    }

    @NonNull
    private Map<Integer, List<Vacina>> toMap(@NonNull List<Vacina> vacinas) {
        Map<Integer, List<Vacina>> map = new TreeMap<>();
        for (Vacina vacina : vacinas) {
            List<Vacina> value = map.get(vacina.getTomou());
            if (value == null) {
                value = new ArrayList<>();
                map.put(vacina.getTomou(), value);
            }
            value.add(vacina);
        }
        return map;
    }

}
