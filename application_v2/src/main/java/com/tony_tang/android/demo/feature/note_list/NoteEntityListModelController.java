package com.tony_tang.android.demo.feature.note_list;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.feature.common.EmptyViewEntity;
import com.tony_tang.android.demo.feature.common.FooterViewEntity;

import javax.inject.Inject;

import hugo.weaving.DebugLog;


public class NoteEntityListModelController extends BaseModelController {

    private final Context context;

    //Q2: 去掉@Inject，编译会有问题。为什么？
    //A2: @Inject 告诉Dagger 大Boss你要给我提供 具体的参数。

    //Context application Context.
    //Q1, 为什么没有问题？
    //A1: 因为Dagger有Module提供了Context//DemoApplicationModule


    //Command + F9
    @DebugLog
    @Inject
    public NoteEntityListModelController(ItemClickListenerCallback itemClickListenerCallback,
                                         EmptyViewEntity emptyViewEntity,
                                         FooterViewEntity footerViewEntity,
                                         Context context) {
        super(itemClickListenerCallback, emptyViewEntity, footerViewEntity);
        this.context = context;
        //4101
    }

    @Override
    public EpoxyModel getModelByType(Object bean) {
        NoteEntity noteEntity = (NoteEntity) bean;
        return new NoteEntityModel_()
                .id(noteEntity.getObjectId())
                .item(noteEntity)
                .context(context)
                .itemClickListener(itemView -> itemClickListenerCallback.onItemClicked(itemView, bean));
    }
}
