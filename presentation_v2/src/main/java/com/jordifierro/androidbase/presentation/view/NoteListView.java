package com.jordifierro.androidbase.presentation.view;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;

public interface NoteListView extends CleanView {

    void showNoteEntityList(List<NoteEntity> notes);
    void showProcessing();

}
