package me.imunize.imunizeme.list;

import android.support.annotation.NonNull;

import me.imunize.imunizeme.models.Vacina;

/**
 * Created by Sr. Décio Montanhani on 14/09/2017.
 */

public class VacinaItem extends ListItem{

    @NonNull
    private Vacina vacina;

    public VacinaItem(@NonNull Vacina vacina) {
        this.vacina = vacina;
    }

    @NonNull
    public Vacina getVacina() {
        return vacina;
    }

    @Override
    public int getType() {
        return TYPE_VACINA;
    }


}
