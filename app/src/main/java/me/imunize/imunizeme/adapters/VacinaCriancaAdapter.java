package me.imunize.imunizeme.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import me.imunize.imunizeme.R;
import me.imunize.imunizeme.list.HeaderItem;
import me.imunize.imunizeme.list.ListItem;
import me.imunize.imunizeme.list.VacinaItem;
import me.imunize.imunizeme.models.Vacina;

/**
 * Created by Sr. Décio Montanhani on 13/09/2017.
 */

public class VacinaCriancaAdapter extends RecyclerView.Adapter {


    @NonNull
    private List<ListItem> items = Collections.emptyList();

    private static ItemLongClickListener itemClickListener;
    private static MenuItemClickListener menuItemClickListener;


    public void setMenuItemClickListener(MenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }

    public void setOnItemClickListener(ItemLongClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public VacinaCriancaAdapter(@NonNull List<ListItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case ListItem.TYPE_HEADER: {
                View view = inflater.inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(view);
            }
            case ListItem.TYPE_VACINA: {
                View itemView = inflater.inflate(R.layout.item_list, parent, false);
                return new VacinaViewHolder(itemView, itemClickListener, menuItemClickListener);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }

    }

    public Vacina getItem(int position)
    {

        int viewType = getItemViewType(position);
        if(viewType == ListItem.TYPE_VACINA) {
            VacinaItem vacina = (VacinaItem) items.get(position);
            return vacina.getVacina();
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType){
            case ListItem.TYPE_HEADER: {
                HeaderItem header = (HeaderItem) items.get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
                Resources rs = ((HeaderViewHolder) viewHolder).conteudo.getResources();
                if (header.getTomou() == 0) {
                    holder.texto.setText("Vacinas Atrasadas");
                    holder.conteudo.setBackgroundColor(rs.getColor(android.R.color.holo_red_dark));
                } else if (header.getTomou() == 1) {
                    holder.texto.setText("Vacinas Tomadas");
                    holder.conteudo.setBackgroundColor(rs.getColor(android.R.color.holo_green_light));
                } else {
                    holder.texto.setText("Próximas Vacinas");
                    holder.conteudo.setBackgroundColor(rs.getColor(android.R.color.holo_orange_light));
                }
                break;
            }
            case ListItem.TYPE_VACINA: {
                VacinaItem vacina = (VacinaItem) items.get(position);
                VacinaViewHolder holder1 = (VacinaViewHolder) viewHolder;
                holder1.nome.setText(vacina.getVacina().getNome());
                holder1.data.setText("13/13/13");
                //holder1.data.setText(vacina.getVacina().getData().toString());
                if (vacina.getVacina().getDose() == 0) {
                    holder1.dose.setText("Dose Única");
                } else {
                    holder1.dose.setText("Dose " + vacina.getVacina().getDose());
                }
                switch (vacina.getVacina().getTomou()) {
                    case 1:
                        holder1.tomou.setImageResource(R.drawable.ic_check_64dp);
                        break;
                    case 0:
                        holder1.tomou.setImageResource(R.drawable.ic_error_list_64dp);
                        break;
                    default:
                        holder1.tomou.setImageResource(R.drawable.ic_proxima_vacina_64dp);
                }
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

}
