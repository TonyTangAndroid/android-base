package com.tony_tang.android.demo.feature.note_detail;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
public interface NoteDetailView {

    void onNoteEntityLoaded(NoteEntity noteEntity);

    String getNoteObjectId();
}
