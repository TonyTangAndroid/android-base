package com.jordifierro.androidbase.presentation.view;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

public interface NoteEditView extends CleanView {

    void showNote(NoteEntity note);

    String getNoteObjectId();

    void onNoteDeleted();
}
