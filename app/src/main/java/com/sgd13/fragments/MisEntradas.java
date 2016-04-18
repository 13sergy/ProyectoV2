package com.sgd13.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.sgd13.adapters.FakeContent;
import com.sgd13.adapters.MyFragmentPagerAdapter;
import com.sgd13.entradas.fragments.AguaFragment;
import com.sgd13.entradas.fragments.HuevosFragment;
import com.sgd13.entradas.fragments.LimpiezaFragment;
import com.sgd13.entradas.fragments.PiensoFragment;
import com.sgd13.entradas.fragments.ResumenFragment;
import com.sgd13.slidingmenu_tabhostviewpager.R;

import java.util.List;
import java.util.Vector;

public class MisEntradas extends Fragment implements OnTabChangeListener,
        OnPageChangeListener {

    private TabHost tabHost;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    private View mView;
    private boolean onResume=false;
    private HorizontalScrollView hScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.tabs_viewpager_layout, container, false);
        hScrollView = (HorizontalScrollView) mView
                .findViewById(R.id.hScrollView);
        initializeTabHost();
        initializeViewPager();

        return mView;
    }



    /**
     * This method is used to initialize the ViewPager with different fragments.
     */
    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();

        fragments.add(new ResumenFragment());
        fragments.add(new HuevosFragment());
        fragments.add(new AguaFragment());
        fragments.add(new PiensoFragment());
        fragments.add(new LimpiezaFragment());


        myViewPagerAdapter = new MyFragmentPagerAdapter(
                getChildFragmentManager(), fragments);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        mViewPager.setAdapter(this.myViewPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    /**
     * This method is used to initialize the tabHost with labels and fake content to simulate.
     */
    private void initializeTabHost() {

        tabHost = (TabHost) mView.findViewById(android.R.id.tabhost);
        tabHost.setup();

        String[] tabIemNames = {"Resumen", "Recogida de huevos", "Recarga de agua", "Recarga de pienso", "Limpieza del gallinero"};
        for (int i = 0; i < tabIemNames.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec("");
            tabSpec.setIndicator(tabIemNames[i]);
            tabSpec.setContent(new FakeContent(getActivity()));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        mViewPager.setCurrentItem(pos);

        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);


    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onResume) {
            View tabView = tabHost.getTabWidget().getChildAt(position);
            int width = hScrollView.getWidth();
            int scrollPos = tabView.getLeft() - (width - tabView.getWidth()) / 2;

            hScrollView.smoothScrollTo(scrollPos, 0);
            onResume=!onResume;
        }
    }


    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        onResume=true;
    }
}
