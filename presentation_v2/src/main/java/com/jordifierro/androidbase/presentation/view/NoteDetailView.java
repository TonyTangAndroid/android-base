package com.jordifierro.androidbase.presentation.view;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.presentation.view.base.CleanView;

public interface NoteDetailView extends CleanView {

    void showNote(NoteEntity note);

    String getNoteObjectId();

}
