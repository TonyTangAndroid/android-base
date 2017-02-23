package com.jordifierro.androidbase.presentation.view;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;

public interface NotesView extends BaseView {

    void showNotes(List<NoteEntity> notes);

    void showNote(String noteObjectId);

    void showExpirationWarning();

}
