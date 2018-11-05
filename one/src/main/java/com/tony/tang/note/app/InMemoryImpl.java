package com.tony.tang.note.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class InMemoryImpl {

    private final Map<String, Pair> map = new HashMap<>();
    private final long ttlInMemory;


    public InMemoryImpl(long ttlInMemory) {
        this.ttlInMemory = ttlInMemory;
    }

    public void put(String keyword, List<NoteEntity> list) {
        map.put(keyword, new Pair(System.currentTimeMillis(), list));
    }

    public boolean isExist(String keyword) {
        return map.containsKey(keyword);
    }

    public boolean isExpired(String keyword) {
        Pair pair = map.get(keyword);
        return pair != null && (System.currentTimeMillis() - pair.updatedTs) > ttlInMemory;
    }

    public boolean isValid(String keyword) {
        return isExist(keyword) && !isExpired(keyword);
    }

    @Nullable
    public List<NoteEntity> get(String keyword) {
        return isValid(keyword) ? map.get(keyword).list : null;
    }

    private final static class Pair {
        private final long updatedTs;
        private final List<NoteEntity> list;

        public Pair(long updatedTs, List<NoteEntity> list) {
            this.updatedTs = updatedTs;
            this.list = list;
        }
    }
}