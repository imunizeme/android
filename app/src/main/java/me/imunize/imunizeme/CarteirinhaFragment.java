package me.imunize.imunizeme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.imunize.imunizeme.adapters.CarteirinhaFragmentPageAdapter;

/**
 * Created by Sr. Décio Montanhani on 17/08/2017.
 */

public class CarteirinhaFragment extends android.support.v4.app.Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fm;


    public CarteirinhaFragment(FragmentManager fm) {
        this.fm = fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carteirinha, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        CarteirinhaFragmentPageAdapter adapter = new CarteirinhaFragmentPageAdapter(fm);
        adapter.addFragment(new AdultoFragment(), "ADULTO");
        adapter.addFragment(new CriancaFragment(), "CRIANÇA");
        viewPager.setAdapter(adapter);
    }

}
