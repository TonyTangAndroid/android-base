package com.tony_tang.android.demo.feature.note_list;

import com.airbnb.epoxy.EpoxyModel;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;

import javax.inject.Inject;


public class NoteEntityListModelController extends BaseModelController {

    @Inject
    public NoteEntityListModelController(ItemCommonClickListenerCallback itemCommonClickListenerCallback, EmptyViewEntity emptyViewEntity, FooterViewEntity footerViewEntity) {
        super(itemCommonClickListenerCallback, emptyViewEntity, footerViewEntity);
    }

    @Override
    public EpoxyModel getModelByType(Object bean) {
        NoteEntity noteEntity = (NoteEntity) bean;
        return new NoteEntityModel_()
                .id(noteEntity.getObjectId())
                .item(noteEntity)
                .itemClickListener(itemView -> itemCommonClickListenerCallback.onItemClicked(itemView, bean));
    }
}
