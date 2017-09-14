package me.imunize.imunizeme.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.imunize.imunizeme.R;

/**
 * Created by Sr. Décio Montanhani on 14/09/2017.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    final CardView conteudo;
    final TextView texto;


    public HeaderViewHolder(View view) {
        super(view);
        conteudo = view.findViewById(R.id.header_content);
        texto = view.findViewById(R.id.header_texto);

    }


}
