package com.jordifierro.androidbase.data.repository;

import javax.inject.Inject;

public class BadgeDataStoreFactory {

    private final NoteDiskDataStore badgeCacheInDisk;
    private final NoteInMemoryDataStore badgeCacheInMemory;
    private final NoteInMemoryImpl noteInMemoryImpl;
    private final NoteCloudDataStore cloudBadgeDataStore;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    BadgeDataStoreFactory(NoteDiskDataStore badgeCacheInDisk,
                          NoteInMemoryDataStore badgeCacheInMemory,
                          NoteInMemoryImpl noteInMemoryImpl,
                          NoteCloudDataStore cloudBadgeDataStore,
                          NoteDiskCacheImpl noteDiskCacheImpl) {
        this.badgeCacheInDisk = badgeCacheInDisk;
        this.badgeCacheInMemory = badgeCacheInMemory;
        this.noteInMemoryImpl = noteInMemoryImpl;
        this.cloudBadgeDataStore = cloudBadgeDataStore;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    BadgeDataStore getDataStore(String objectId) {
        BadgeDataStore badgeDataStore;
        if (noteInMemoryImpl.isValid(objectId)) {
            System.out.println("hit memory");
            badgeDataStore = badgeCacheInMemory;
        } else if (this.noteDiskCacheImpl.isValid(objectId)) {
            System.out.println("hit disk");
            badgeDataStore = badgeCacheInDisk;
        } else {
            System.out.println("hit cloud");
            badgeDataStore = cloudBadgeDataStore;
        }

        return badgeDataStore;
    }

    NoteCloudDataStore getCloudDataStore() {
        return cloudBadgeDataStore;
    }

    NoteDiskDataStore noteDiskDataStore() {
        return badgeCacheInDisk;
    }


}
