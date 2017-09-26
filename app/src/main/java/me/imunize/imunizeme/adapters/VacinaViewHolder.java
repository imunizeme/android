package me.imunize.imunizeme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.imunize.imunizeme.R;
import me.imunize.imunizeme.models.Vacina;


/**
 * Created by Sr. Décio Montanhani on 13/09/2017.
 */
public class VacinaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {


    final TextView nome;
    final TextView data;
    final TextView dose;
    final ImageView tomou;
    final ItemLongClickListener clicklistener;
    private final MenuItemClickListener menuItemClickListener;


    public VacinaViewHolder(View view, ItemLongClickListener itemClickListener, MenuItemClickListener menuItemClickListener) {
        super(view);
        this.clicklistener = itemClickListener;
        this.menuItemClickListener = menuItemClickListener;

        nome = view.findViewById(R.id.item_nome_vacina);
        data = view.findViewById(R.id.item_data_vacina);
        dose = view.findViewById(R.id.item_dose_vacina);
        tomou = view.findViewById(R.id.item_icon_vacina);

        view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if(clicklistener != null){
            clicklistener.onItemClick(getAdapterPosition());
        }

        MenuItem itemDetalhes = menu.add("Informações da Vacina");
        itemDetalhes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItemClickListener != null){
                    menuItemClickListener.onMenuItemClick(getAdapterPosition());
                }
                return false;
            }
        });

    }
}
