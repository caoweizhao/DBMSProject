package com.example.administrator.dbmsproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dbmsproject.AddressLoadError;
import com.example.administrator.dbmsproject.AddressWrapper;
import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.adapter.MyAdapter;
import com.example.administrator.dbmsproject.model.helper.AddressHelper;
import com.example.administrator.dbmsproject.model.listener.AddressItemClickListener;
import com.example.administrator.dbmsproject.model.locationbean.CityBean;
import com.example.administrator.dbmsproject.model.locationbean.CountryBean;
import com.example.administrator.dbmsproject.model.locationbean.IAddress;
import com.example.administrator.dbmsproject.model.wrapper.CountryListWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-3-28.
 */

public class CountryFragment extends Fragment {
    public AddressItemClickListener mAddressItemClickListener;
    RecyclerView mRecyclerView;
    private List<CountryBean> mCountryBeanList = new ArrayList<>();
    private MyAdapter myAdapter;

    private CityBean mCityBean;

    public CountryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mCityBean = (CityBean) getArguments().getSerializable("city");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!(getActivity() instanceof AddressItemClickListener)) {
            throw new RuntimeException("Activity must implements AddressItemOnclickListener!");
        }
        mAddressItemClickListener = (AddressItemClickListener) getActivity();
        getData();
    }

    private void initRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(mCountryBeanList);
        mRecyclerView.setAdapter(myAdapter);
    }

    public static CountryFragment newInstance(@NonNull CityBean cityBean) {
        CountryFragment countryFragment = new CountryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", cityBean);
        countryFragment.setArguments(bundle);
        return countryFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void getData() {
        new AddressHelper().getCountryList(mCityBean.getProvinceId(), mCityBean.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReady(CountryListWrapper cityListWrapper) {
        mCountryBeanList = cityListWrapper.getCountryBeanList();
        initRecycleView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadFail(AddressLoadError addressLoadError) {
    }

    @Subscribe
    public void ItemClick(AddressWrapper addressWrapper) {
        if (isResumed()) {
            IAddress address = addressWrapper.getIAddress();
            mAddressItemClickListener.onItemClick(address);
        }
    }
}
