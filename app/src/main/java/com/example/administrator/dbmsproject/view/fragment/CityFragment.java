package com.example.administrator.dbmsproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.administrator.dbmsproject.model.locationbean.IAddress;
import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;
import com.example.administrator.dbmsproject.model.wrapper.CityListWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-3-28.
 */

public class CityFragment extends Fragment {
    public AddressItemClickListener mAddressItemClickListener;
    RecyclerView mRecyclerView;
    private List<CityBean> mCityBeanList = new ArrayList<>();
    private MyAdapter myAdapter;
    private ProvinceBean mProvinceBean;

    public CityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("CityFragment", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("CityFragment", "city -- oncreateView");
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mProvinceBean = (ProvinceBean) getArguments().getSerializable("province");
        }

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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("CityFragment", "city -- ondestroyView");
        EventBus.getDefault().unregister(this);
    }

    private void initRecycleView() {
        myAdapter = new MyAdapter(mCityBeanList);
        mRecyclerView.setAdapter(myAdapter);
    }

    public static CityFragment newInstance(@NonNull ProvinceBean provinceBean) {
        CityFragment cityFragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("province", provinceBean);
        cityFragment.setArguments(bundle);
        return cityFragment;
    }

    public void getData() {
        new AddressHelper().getCityList(mProvinceBean.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReady(CityListWrapper cityListWrapper) {
        mCityBeanList = cityListWrapper.getCityBeanList();
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
