package me.imunize.imunizeme.list;

import android.support.annotation.NonNull;

/**
 * Created by Sr. Décio Montanhani on 14/09/2017.
 */

public class HeaderItem extends ListItem {

    @NonNull
    private int tomou;

    public HeaderItem(@NonNull int tomou) {
        this.tomou = tomou;
    }

    @NonNull
    public int getTomou() {
        return tomou;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
