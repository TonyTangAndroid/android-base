package com.tony_tang.android.demo.feature.common;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.jordifierro.androidbase.domain.constant.Constants;
import com.tony_tang.android.demo.BuildConfig;

import java.util.List;


public abstract class BaseModelController extends TypedEpoxyController<List<?>> {

    protected final ItemCommonClickListenerCallback itemCommonClickListenerCallback;

    @AutoModel
    FooterModel_ footerModel;

    @AutoModel
    EmptyViewModel_ emptyViewModel;
    FooterViewEntity footerViewEntity;
    EmptyViewEntity emptyViewEntity;


    public BaseModelController(ItemCommonClickListenerCallback itemCommonClickListenerCallback,
                               EmptyViewEntity emptyViewEntity,
                               FooterViewEntity footerViewEntity) {
        this.itemCommonClickListenerCallback = itemCommonClickListenerCallback;
        this.emptyViewEntity = emptyViewEntity;
        this.footerViewEntity = footerViewEntity;
        setDebugLoggingEnabled(true);
    }

    public void bindDataListToUI(List<?> noticeEntityList) {
        setData(noticeEntityList);
    }

    @Override
    protected void buildModels(List<?> dataList) {

        buildEmptyView(dataList);
        buildDataListView(dataList);
        buildFooterView(dataList);
    }


    public void bindEmptyViewEntityToUI(EmptyViewEntity emptyViewEntity) {
        this.emptyViewEntity = emptyViewEntity;
        setData(getCurrentData());
    }

    public void bindFooterViewEntityToUI(FooterViewEntity footerViewEntity) {
        this.footerViewEntity = footerViewEntity;
        setData(getCurrentData());
    }


    private void buildFooterView(List<?> dataList) {
        footerModel.footerViewEntity(footerViewEntity)
                .clickListener(v -> itemCommonClickListenerCallback.onFooterClicked())
                .addIf(dataList != null && dataList.size() >= Constants.DEFAULT_LIMIT_VALUE, this);
    }

    public abstract EpoxyModel getModelByType(Object bean);

    public void buildDataListView(List<?> dataList) {
        if (!isEmptyList(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                add(getModelByType(dataList.get(i)));
            }
        }

    }

    private void buildEmptyView(List<?> dataList) {
        emptyViewModel.emptyViewEntity(emptyViewEntity)
                .retryListener(v -> itemCommonClickListenerCallback.retry())
                .bottomViewClickListener(v -> itemCommonClickListenerCallback.bottomViewClicked())
                .addIf(isEmptyList(dataList), this);
    }

    protected boolean isEmptyList(List<?> dataList) {
        return dataList == null || dataList.size() == 0;
    }

    protected void onExceptionSwallowed(RuntimeException exception) {
        if (BuildConfig.DEBUG) {
            throw exception;
        }
    }

    public interface ItemCommonClickListenerCallback {
        void onItemClicked(View view, Object entity);

        void onFooterClicked();

        void retry();

        void bottomViewClicked();
    }

}
