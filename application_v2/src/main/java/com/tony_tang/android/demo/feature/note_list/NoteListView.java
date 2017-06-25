package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;

/**

 */

public interface NoteListView {
    void showNoteEntityList(List<NoteEntity> noteEntityList);
}
