package com.tony.tang.note.app;


import java.util.List;

import javax.annotation.Nonnull;


public interface NoteEntityCache {

    List<NoteEntity> list(String keyword);

    void delete(long id);

    void insertOrReplace(@Nonnull List<NoteEntity> list);

    void insertOrReplace(@Nonnull NoteEntity list);

    boolean isExist(long id);

    boolean isExist(String id);

}