package com.example.administrator.dbmsproject.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.listener.AddressItemClickListener;
import com.example.administrator.dbmsproject.model.locationbean.CityBean;
import com.example.administrator.dbmsproject.model.locationbean.CountryBean;
import com.example.administrator.dbmsproject.model.locationbean.IAddress;
import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;
import com.example.administrator.dbmsproject.view.fragment.CityFragment;
import com.example.administrator.dbmsproject.view.fragment.CountryFragment;
import com.example.administrator.dbmsproject.view.fragment.ProvinceFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017-3-28.
 */

public class SelectAddressActivity extends AppCompatActivity implements AddressItemClickListener {

    private View mDecorView;

    public static final String TYPE_PROVINCE = "province";
    public static final String TYPE_CITY = "city";
    public static final String TYPE_COUNTRY = "country";
    public static final String COUNTRY_NAME_KEY = "city_name";

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ProvinceBean mProvinceBean;
    private CityBean mCityBean;

    private int currentPage = 0;

    @Override
    public void onItemClick(IAddress iAddress) {
        Toast.makeText(this, "address:"+iAddress.getName()+"--"+iAddress.getId(), Toast.LENGTH_SHORT).show();
        if (iAddress instanceof ProvinceBean) {
            mProvinceBean = (ProvinceBean) iAddress;
            mCollapsingToolbarLayout.setTitle(mProvinceBean.getName());
            currentPage = 1;
            setFragment(TYPE_CITY);
        } else if (iAddress instanceof CityBean) {
            mCityBean = (CityBean) iAddress;
            mCollapsingToolbarLayout.setTitle(mCityBean.getName());
            currentPage = 2;
            setFragment(TYPE_COUNTRY);
        } else if (iAddress instanceof CountryBean) {
            CountryBean countryBean = (CountryBean) iAddress;
            Intent intent = new Intent(SelectAddressActivity.this, MainActivity.class);
            intent.putExtra(COUNTRY_NAME_KEY, countryBean.getName());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Retention(RetentionPolicy.CLASS)
    @StringDef({TYPE_PROVINCE, TYPE_CITY, TYPE_COUNTRY})
    @interface TYPE_FRAGMENT {
    }

    private Fragment provinceFragment;
    private Fragment cityFragment;
    private Fragment countryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("SelectAddressActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_address);
        mDecorView = getWindow().getDecorView();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout_select);
        initToolBar();
        setFragment(TYPE_PROVINCE);

    }

    private Toolbar mToolbar;

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout.setTitle("中国");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                if(currentPage == 1){
                    mCollapsingToolbarLayout.setTitle(mProvinceBean.getName());
                }else if(currentPage == 0){
                    mCollapsingToolbarLayout.setTitle("中国");
                }
                onBackPressed();

            }
        });
    }

    private void setFragment(@TYPE_FRAGMENT String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (tag.equals(TYPE_PROVINCE)) {
            provinceFragment = ProvinceFragment.newInstance();
            transaction.replace(R.id.m_container, provinceFragment, TYPE_PROVINCE);
            transaction.commit();

        } else if (tag.equals(TYPE_CITY)) {
            cityFragment = CityFragment.newInstance(mProvinceBean);
            transaction.replace(R.id.m_container, cityFragment, TYPE_CITY);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (tag.equals(TYPE_COUNTRY)) {
            countryFragment = CountryFragment.newInstance(mCityBean);
            transaction.replace(R.id.m_container, countryFragment, TYPE_COUNTRY);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideSystemUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
