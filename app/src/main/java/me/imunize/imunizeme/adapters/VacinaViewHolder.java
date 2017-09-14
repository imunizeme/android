package me.imunize.imunizeme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.imunize.imunizeme.R;


/**
 * Created by Sr. Décio Montanhani on 13/09/2017.
 */
public class VacinaViewHolder extends RecyclerView.ViewHolder {


    final TextView nome;
    final TextView data;
    final TextView dose;
    final ImageView tomou;

    public VacinaViewHolder(View view) {
        super(view);

        nome = view.findViewById(R.id.item_nome_vacina);
        data = view.findViewById(R.id.item_data_vacina);
        dose = view.findViewById(R.id.item_dose_vacina);
        tomou = view.findViewById(R.id.item_icon_vacina);

    }
}
