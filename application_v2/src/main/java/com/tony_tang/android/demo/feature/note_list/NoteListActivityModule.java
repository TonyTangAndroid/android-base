package com.tony_tang.android.demo.feature.note_list;

import com.tony_tang.android.demo.common.scope.ActivityScope;
import com.tony_tang.android.demo.feature.common.BaseModelController;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

import dagger.Binds;
import dagger.Module;

//@DebugLog
@Module
public abstract class NoteListActivityModule {

    @ActivityScope
    @Binds//绑定
    //重点一
    //0, Binds对应的类也是抽象类。
    //1, 只能传递一个参数。/
    //2, 传递参数的class已经实现了返回类型。
    //3, 它是抽象方法。Dagger会自动帮我们实现这个抽象方法。
    //4, 为什么只有一个参数？
    //5, Dagger只能根据一个参数为我们生成代码。
    abstract BaseListView provideMainView
            (NoteListActivity mainActivity);

    @ActivityScope
    @Binds
    abstract BaseModelController.ItemClickListenerCallback provideItemClickListenerCallback(NoteListActivity mainActivity);


}
