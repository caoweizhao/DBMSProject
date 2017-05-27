package com.example.administrator.dbmsproject.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.wrapper.TicketListWrapper;
import com.example.administrator.dbmsproject.presenter.IPresenter;
import com.example.administrator.dbmsproject.presenter.MainPresenter;
import com.example.administrator.dbmsproject.view.fragment.ClientTicketFragment;
import com.example.administrator.dbmsproject.view.fragment.RefundsTicketFragment;
import com.example.administrator.dbmsproject.view.fragment.SearchFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements IView {


    public static final String TRAIN_INFO_DATA = "TRAIN_INFO_DATA";
    public static final String TRAIN_START_DATE = "START_DATE";
    public static final String SOURCE_POS = "SOURCE_POS";
    public static final String DESTINATION_POS = "DESTINATION_POS";

    public static final int TYPE_RETRIEVE_TICKET = R.id.retrieve_ticket_page;
    public static final int TYPE_SEARCH_TRAIN = R.id.search_train_page;

    private boolean isExpand = false;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    SearchView mSearchView;
    ActionBar mActionBar;
    private Toolbar mToolBar;
    private MainPresenter mPresenter;

    private FragmentManager mFragmentManager;
    private SearchFragment mSearchFragment;
    private RefundsTicketFragment mRefundsTicketFragment;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mFragmentManager = getSupportFragmentManager();
        initToolBar();
        initView();
        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter = new MainPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    protected void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }

    private void initFragment() {
        mNavigationView.setCheckedItem(R.id.search_train_page);
        setFragment(TYPE_SEARCH_TRAIN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                isExpand = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isExpand = false;
                return true;
            }
        });
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("输入车次号搜索");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onSearchSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

   /* @Override
    public void onBackPressed() {
        if (isExpand) {
            mSearchView.clearFocus();
            mSearchView.onActionViewCollapsed();
            mActionBar.setDisplayHomeAsUpEnabled(false);
        } else {
            super.onBackPressed();
        }
    }*/

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mToolBar.setNavigationOnClickListener(e -> {
                if (isExpand) {
                    onBackPressed();
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            });
        }
    }

    private void initView() {
        mNavigationView = (NavigationView) findViewById(R.id.my_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.retrieve_ticket_page:
                    setFragment(TYPE_RETRIEVE_TICKET);
                    break;
                case R.id.search_train_page:
                    setFragment(TYPE_SEARCH_TRAIN);
                    break;
            }
            mDrawerLayout.closeDrawer(Gravity.START);
            return true;
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private SweetAlertDialog errorDialog;

    @Override
    public void showErrorMessage(String msg) {
        errorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        errorDialog.setTitleText(msg)
                .setCancelable(true);
        errorDialog.setCanceledOnTouchOutside(true);
        errorDialog.show();
    }

    private SweetAlertDialog sad;

    @Override
    public void showLoading() {
        sad = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sad.setTitleText("正在加载，请稍后...");
        sad.setCancelable(false);
        sad.show();
    }

    @Override
    public void dismissLoading() {
        if (sad != null) {
            if (sad.isShowing()) {
                sad.dismissWithAnimation();
            }
        }
    }

    @Override
    public void setTicketFragment(TicketListWrapper ticketListWrapper) {
        ClientTicketFragment clientTicketFragment = ClientTicketFragment.newInstance(ticketListWrapper);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.root_container, clientTicketFragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setFragment(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (position) {
            case TYPE_RETRIEVE_TICKET:
                if (mRefundsTicketFragment == null) {
                    mRefundsTicketFragment = new RefundsTicketFragment();
                }
                transaction.replace(R.id.root_container, mRefundsTicketFragment, null);
                mToolBar.setTitle("退票");
                break;
            case TYPE_SEARCH_TRAIN:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                }
                transaction.replace(R.id.root_container, mSearchFragment, null);
                mToolBar.setTitle("班次查询");
                break;
        }
        transaction.commit();
    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    public void exitClicked(View view) {
        finish();
    }

    public void checkTicket(String id) {
        mPresenter.onCheckTicketButtonClicked(id);
    }
}
