package com.tony_tang.android.demo.presentation.view;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.tony_tang.android.demo.presentation.view.base.CleanView;

public interface NoteDetailView extends CleanView {

    void showNote(NoteEntity note);

    String getNoteObjectId();

}
