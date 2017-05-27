package com.example.administrator.dbmsproject.view.fragment;

import android.os.Bundle;
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
import com.example.administrator.dbmsproject.model.Contract;
import com.example.administrator.dbmsproject.model.helper.AddressHelper;
import com.example.administrator.dbmsproject.model.listener.AddressItemClickListener;
import com.example.administrator.dbmsproject.model.locationbean.IAddress;
import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;
import com.example.administrator.dbmsproject.model.wrapper.ProvinceListWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-3-28.
 */

public class ProvinceFragment extends Fragment {
    public AddressItemClickListener mAddressItemClickListener;
    RecyclerView mRecyclerView;

    private List<ProvinceBean> mProvinceBeanList = new ArrayList<>();
    private MyAdapter myAdapter;

    public ProvinceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void onResume() {
        Log.d("ProvinceFragment", "onResume");
        super.onResume();
        getData();
    }

    private void initRecycleView() {
        myAdapter = new MyAdapter(mProvinceBeanList);
        mRecyclerView.setAdapter(myAdapter);
    }

    public static ProvinceFragment newInstance() {
        ProvinceFragment provinceFragment = new ProvinceFragment();
        return provinceFragment;
    }

    @Override
    public void onDestroyView() {
        Log.d("ProvinceFragment", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void getData() {
        AddressHelper addressHelper = new AddressHelper();
        addressHelper.getProvinceList(Contract.ADDRESS_PROVINCES);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReady(ProvinceListWrapper provinceListWrapper) {
        for (ProvinceBean pro:provinceListWrapper.getProvinceBeanList()
             ) {
            Log.d("ProvinceFragment","address:"+pro.getName()+"--"+pro.getId());
        }
        mProvinceBeanList = provinceListWrapper.getProvinceBeanList();
        initRecycleView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadFail(AddressLoadError addressLoadError) {
        Log.d("ProvinceFragment", "getAddressFail");
    }

    @Subscribe
    public void ItemClick(AddressWrapper addressWrapper) {
        if (isResumed()) {
            IAddress address = addressWrapper.getIAddress();
            mAddressItemClickListener.onItemClick(address);
        }
    }
}
