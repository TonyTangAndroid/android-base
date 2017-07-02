package com.tony_tang.android.demo.common.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tony_tang.android.demo.R;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.common.CleanViewStatus;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.EndlessRecyclerOnScrollListenerTrial;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;
import com.tony_tang.android.demo.feature.common.LoadingStatus;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public abstract class BaseModelListActivity extends PresenterActivity implements BaseListView,
        EndlessRecyclerOnScrollListenerTrial.RecyclerViewScrollListener,
        BaseModelController.ItemClickListenerCallback,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject
    EmptyViewEntity defaultEmptyViewEntity;

    @Inject
    FooterViewEntity defaultFooterViewEntity;
    @BindView(R.id.rv_entity_list)
    RecyclerView rvEntityList;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @LoadingStatus
    private int status;

    protected abstract BaseModelController controller();

    @Override
    public void initUI() {
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvEntityList.setLayoutManager(layoutManager);
        rvEntityList.addOnScrollListener(new EndlessRecyclerOnScrollListenerTrial(layoutManager, this));
        rvEntityList.setAdapter(controller().getAdapter());
        rvEntityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        loadData();
    }

    private void loadData() {
        if (isIdle()) {
            status = CleanViewStatus.LOADING;
            presenter().loadData();
        }
    }

    protected abstract BaseListPresenter presenter();


    private boolean isIdle() {
        return status == CleanViewStatus.IDLE;
    }

    protected int getLayoutId() {
        return R.layout.activity_entity_list;
    }


    @Override
    public void showEntityList(List<?> entityList) {
        controller().bindDataListToUI(entityList);
    }

    @Override
    public void showProcessing() {
        super.showLoader();
    }

    @Override
    public void onRetrievingDataCompleted() {
        resetViewStatus();
    }

    @Override
    public void showLoader() {
        controller().bindEmptyViewEntityToUI(defaultEmptyViewEntity);
    }


    @Override
    public void onFooterClicked() {
        loadMoreData();
    }

    @Override
    public void retry() {
        loadData();
    }

    @Override
    public void bottomViewClicked() {
        loadMoreData();
    }


    @Override
    public void onRefresh() {
        if (isIdle()) {
            status = CleanViewStatus.REFRESHING;
            presenter().refreshData();
        } else {
            swipeRefreshLayout.setEnabled(false);
        }

    }

    @Override
    public void onBottomToBeReached() {
        loadMoreData();
    }

    private void loadMoreData() {
        if (isIdle()) {
            status = CleanViewStatus.LOADING_MORE;
            controller().bindFooterViewEntityToUI(defaultFooterViewEntity.withShowFooterView(true));
            presenter().loadMoreData();
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        String errorMessage = retrieveMessage(throwable);
        switch (status) {
            case CleanViewStatus.REFRESHING:
            case CleanViewStatus.LOADING:
                doShowErrorView(errorMessage);
                break;
            case CleanViewStatus.LOADING_MORE:
                showLoadingMoreError(errorMessage);
                break;
            case CleanViewStatus.IDLE:
            default:
                break;
        }
    }

    private void showLoadingMoreError(String errorMessage) {
        FooterViewEntity footerViewEntity = FooterViewEntity.builder()
                .showLoading(false)
                .footerHint(errorMessage)
                .showFooterView(true)
                .build();
        controller().bindFooterViewEntityToUI(footerViewEntity);
    }

    private String retrieveMessage(Throwable throwable) {
        if (isNetworkNotAvailable(throwable)) {
            return getString(R.string.network_not_available);
        } else if (isNetworkConnectionTimeOut(throwable)) {
            return getString(R.string.network_connection_timeout);
        } else {
            return getString(R.string.unknown_error);
        }
    }

    private boolean isNetworkConnectionTimeOut(Throwable throwable) {
        return throwable instanceof ConnectException || throwable instanceof SocketTimeoutException || throwable instanceof SocketException;
    }

    private boolean isNetworkNotAvailable(Throwable throwable) {
        return throwable instanceof UnknownHostException;
    }

    private void resetViewStatus() {
        status = CleanViewStatus.IDLE;
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
    }

    private void doShowErrorView(String message) {
        EmptyViewEntity emptyViewEntity = EmptyViewEntity.builder()
                .showImage(showEmptyImageView())
                .imageDrawableRes(getDefaultEmptyViewImageDrawableResourceId())
                .showLoading(false)
                .showRetry(true)
                .middleHint1(message)
                .build();
        controller().bindEmptyViewEntityToUI(emptyViewEntity);
    }

    private int getDefaultEmptyViewImageDrawableResourceId() {
        return R.drawable.ic_launcher;
    }

    private boolean showEmptyImageView() {
        return false;
    }

}
