package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class NoteInMemoryImpl {

    private final Map<String, Pair> map = new HashMap<>();
    private final long ttlInMemory;


    public NoteInMemoryImpl(long ttlInMemory) {
        this.ttlInMemory = ttlInMemory;
    }

    public void put(NoteEntity noteEntity) {
        map.put(noteEntity.objectId(), new Pair(System.currentTimeMillis(), noteEntity));
    }

    public boolean isExist(String objectId) {
        return map.containsKey(objectId);
    }

    public boolean isExpired(String objectId) {
        Pair pair = map.get(objectId);
        return pair != null && (System.currentTimeMillis() - pair.updatedTs) > ttlInMemory;
    }

    public boolean isValid(String objectId) {
        return isExist(objectId) && !isExpired(objectId);
    }

    @Nullable
    public NoteEntity get(String objectId) {
        return isValid(objectId) ? map.get(objectId).noteEntity : null;
    }

    public void evict(String objectId) {
        map.remove(objectId);
    }

    private final static class Pair {
        private final long updatedTs;
        private final NoteEntity noteEntity;

        public Pair(long updatedTs, NoteEntity noteEntity) {
            this.updatedTs = updatedTs;
            this.noteEntity = noteEntity;
        }
    }
}