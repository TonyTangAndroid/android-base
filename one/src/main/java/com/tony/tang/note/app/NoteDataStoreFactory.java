package com.tony.tang.note.app;

import javax.inject.Inject;

public class NoteDataStoreFactory {

    private final NoteDiskDataStore badgeCacheInDisk;
    private final InMemoryDataStore badgeCacheInMemory;
    private final InMemoryImpl noteInMemoryImpl;
    private final NoteCloudDataStore cloudBadgeDataStore;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    NoteDataStoreFactory(NoteDiskDataStore badgeCacheInDisk,
                         InMemoryDataStore badgeCacheInMemory,
                         InMemoryImpl noteInMemoryImpl,
                         NoteCloudDataStore cloudBadgeDataStore,
                         NoteDiskCacheImpl noteDiskCacheImpl) {
        this.badgeCacheInDisk = badgeCacheInDisk;
        this.badgeCacheInMemory = badgeCacheInMemory;
        this.noteInMemoryImpl = noteInMemoryImpl;
        this.cloudBadgeDataStore = cloudBadgeDataStore;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    NoteDataStore getDataStore(String keyword) {
        NoteDataStore dataStore;
        if (noteInMemoryImpl.isValid(keyword)) {
            System.out.println("hit memory");
            dataStore = badgeCacheInMemory;
        } else if (this.noteDiskCacheImpl.isExist(keyword)) {
            System.out.println("hit disk");
            dataStore = badgeCacheInDisk;
        } else {
            System.out.println("hit cloud");
            dataStore = cloudBadgeDataStore;
        }

        return dataStore;
    }

    NoteCloudDataStore getCloudDataStore() {
        return cloudBadgeDataStore;
    }

    NoteDiskDataStore noteDiskDataStore() {
        return badgeCacheInDisk;
    }


}
