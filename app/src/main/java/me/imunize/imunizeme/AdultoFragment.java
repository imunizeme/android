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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import me.imunize.imunizeme.adapters.ItemLongClickListener;
import me.imunize.imunizeme.adapters.MenuItemClickListener;
import me.imunize.imunizeme.adapters.VacinaAdultoAdapter;
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

public class AdultoFragment extends android.support.v4.app.Fragment {


    @NonNull
    private List<ListItem> items = new ArrayList<>();
    RecyclerView carteirinha;
    SwipeRefreshLayout swipe;
    private List<Vacina> lista = new ArrayList<>();
    private DateFormat postgres = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_carteirinha_adulto, container, false);
        final FragmentActivity c = getActivity();
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.adulto_swipe);
        carteirinha = (RecyclerView) view.findViewById(R.id.adulto_lista_vacinas);

        //Faz requisição e retorna vacinas tomadas e não tomadas
        //Chama uma classe que vai percorrer a lista e decidir qual está atrasada e qual não está
        //o metódo retorna uma lista do tipo <Vacina>, com um


        UsuarioService usuarioService = ServiceGenerator.createService();

        SPHelper helper = new SPHelper(getContext());

        Call<List<Vacina>> call = usuarioService.pegarVacinasAdulto(helper.pegaToken(), helper.pegaIdUsuario());

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

                            String dataString = new SPHelper(getContext()).pegaAniversario();
                            Date date = new Date();
                            try {
                                date = postgres.parse(dataString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Calendar c = Calendar.getInstance();
                            c.setTime(date);
                            c.add(Calendar.MONTH, vacina.getIdadeInicio());
                            vacina.setData(c.getTime());
                            Date today = new Date();
                            if(today.before(vacina.getData())){
                                vacina.setTomou(2);
                            }else{
                                vacina.setTomou(0);
                            }
                            lista.add(vacina);
                            /*
                            *Código Antigo
                            vacina.setTomou(0);
                            lista.add(vacina);*/
                        }
                    }

                    lista = ordenaVacinas(lista);
                    geraItensOrdenados(lista);
                    if(carteirinha != null){
                        if(carteirinha.getAdapter() != null){
                            carteirinha.getAdapter().notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Vacina>> call, Throwable t) {

            }
        });

       /* try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        carteirinha.setLayoutManager(linearLayoutManager);

        VacinaAdultoAdapter adapter = new VacinaAdultoAdapter(items);

        carteirinha.setAdapter(adapter);

        registerForContextMenu(carteirinha);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Atualizado!", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        });

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


        return view;

    }

    private void geraItensOrdenados(List<Vacina> lista) {
        Map<Integer, List<Vacina>> vacinas = toMap(lista);

        for (int tomou : vacinas.keySet()) {
            HeaderItem header = new HeaderItem(tomou);
            items.add(header);
            //Adiciona Header
            for (Vacina vacina : vacinas.get(tomou)) {
                //Adiciona vacina
                VacinaItem item = new VacinaItem(vacina);
                items.add(item);
            }
        }
    }

    /*@NonNull
    private List<Vacina> getMockVacinas() {
        List<Vacina> lista = new ArrayList<>();
        lista.add(new Vacina("H1N1", 1, "15/04/2017", 0));
        lista.add(new Vacina("Meningocócica", 1, "13/09/2000", 0));
        lista.add(new Vacina("Hepatite B", 2, "13/09/2018", 2));
        lista.add(new Vacina("Hepatite B", 3, "13/09/2019", 2));
        lista.add(new Vacina("Hepatite B", 1, "27/08/1996", 1));
        lista.add(new Vacina("BCG", 0, "27/08/1996", 1));
        return lista;
    }*/

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
