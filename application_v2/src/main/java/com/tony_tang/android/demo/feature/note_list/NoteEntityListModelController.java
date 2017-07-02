package com.tony_tang.android.demo.feature.note_list;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.jordifierro.androidbase.domain.constant.Constants;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.BuildConfig;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.EmptyViewModel_;
import com.tony_tang.android.demo.feature.common.FooterModel_;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;

import java.util.List;

import javax.inject.Inject;


public class NoteEntityListModelController extends TypedEpoxyController<List<NoteEntity>> {

    protected final ItemClickListenerCallback itemClickListenerCallback;
    protected final ItemCommonClickListenerCallback itemCommonClickListenerCallback;

    @AutoModel
    FooterModel_ footerModel;

    @AutoModel
    EmptyViewModel_ emptyViewModel;
    FooterViewEntity footerViewEntity;
    EmptyViewEntity emptyViewEntity;


    @Inject
    public NoteEntityListModelController(ItemClickListenerCallback itemClickListenerCallback,
                                         ItemCommonClickListenerCallback itemCommonClickListenerCallback,
                                         EmptyViewEntity emptyViewEntity,
                                         FooterViewEntity footerViewEntity) {
        this.itemClickListenerCallback = itemClickListenerCallback;
        this.itemCommonClickListenerCallback = itemCommonClickListenerCallback;
        this.emptyViewEntity = emptyViewEntity;
        this.footerViewEntity = footerViewEntity;
        setDebugLoggingEnabled(true);
    }

    public void bindDataListToUI(List<NoteEntity> noticeEntityList) {
        setData(noticeEntityList);
    }

    public void bindEmptyViewEntityToUI(EmptyViewEntity emptyViewEntity) {
        this.emptyViewEntity = emptyViewEntity;
        setData(getCurrentData());
    }

    public void bindFooterViewEntityToUI(FooterViewEntity footerViewEntity) {
        this.footerViewEntity = footerViewEntity;
        setData(getCurrentData());
    }


    @Override
    protected void buildModels(List<NoteEntity> objectList) {

        buildEmptyView(objectList);
        buildDataListView(objectList);
        buildFooterView(objectList);
    }

    private void buildFooterView(List<?> noticeEntityList) {
        footerModel.footerViewEntity(footerViewEntity)
                .clickListener(v -> itemCommonClickListenerCallback.onFooterClicked())
                .addIf(noticeEntityList != null && noticeEntityList.size() >= Constants.DEFAULT_LIMIT_VALUE, this);
    }

    private void buildDataListView(List<NoteEntity> noticeEntityList) {
        if (!isEmptyList(noticeEntityList)) {
            for (int i = 0; i < noticeEntityList.size(); i++) {
                NoteEntity noticeEntity = noticeEntityList.get(i);
                add(getModelByType(i, noticeEntity));
            }

        }

    }

    public EpoxyModel getModelByType(int index, NoteEntity bean) {

        return new NoteEntityModel_()
                .id(bean.getObjectId())
                .index(index + 1)
                .item(bean)
                .itemClickListener(itemView -> itemClickListenerCallback.onItemClicked(itemView, bean));
    }


    private void buildEmptyView(List<NoteEntity> noticeEntityList) {
        emptyViewModel.emptyViewEntity(emptyViewEntity)
                .retryListener(v -> itemCommonClickListenerCallback.retry())
                .bottomViewClickListener(v -> itemCommonClickListenerCallback.bottomViewClicked())
                .addIf(isEmptyList(noticeEntityList), this);
    }

    private boolean isEmptyList(List<NoteEntity> noticeEntityList) {
        return noticeEntityList == null || noticeEntityList.size() == 0;
    }

    protected void onExceptionSwallowed(RuntimeException exception) {
        if (BuildConfig.DEBUG) {
            throw exception;
        }
    }

    public interface ItemClickListenerCallback {
        void onItemClicked(View view, NoteEntity entity);

    }

    public interface ItemCommonClickListenerCallback {
        void onFooterClicked();

        void retry();

        void bottomViewClicked();
    }

}
