package me.imunize.imunizeme.list;

/**
 * Created by Sr. Décio Montanhani on 14/09/2017.
 */

public abstract class ListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_VACINA = 1;

    abstract public int getType();

}
