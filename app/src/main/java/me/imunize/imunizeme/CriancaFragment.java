package me.imunize.imunizeme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sr. Décio Montanhani on 17/08/2017.
 */

public class CriancaFragment extends android.support.v4.app.Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carteirinha_crianca, container, false);

        return view;

    }
}
