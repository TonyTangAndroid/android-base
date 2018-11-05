package com.tony.tang.movie.data;

import javax.inject.Inject;

class MovieEntityDataStoreFactory {

    private final MovieEntityDiskDataStore badgeCacheInDisk;
    private final InMemoryDataStore badgeCacheInMemory;
    private final InMemoryImpl noteInMemoryImpl;
    private final MovieEntityCloudDataStore cloudBadgeDataStore;
    private final MovieEntityDiskCacheImpl noteDiskCacheImpl;

    @Inject
    MovieEntityDataStoreFactory(MovieEntityDiskDataStore badgeCacheInDisk,
                                InMemoryDataStore badgeCacheInMemory,
                                InMemoryImpl noteInMemoryImpl,
                                MovieEntityCloudDataStore cloudBadgeDataStore,
                                MovieEntityDiskCacheImpl noteDiskCacheImpl) {
        this.badgeCacheInDisk = badgeCacheInDisk;
        this.badgeCacheInMemory = badgeCacheInMemory;
        this.noteInMemoryImpl = noteInMemoryImpl;
        this.cloudBadgeDataStore = cloudBadgeDataStore;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    MovieEntityDataStore getDataStore(String keyword) {
        MovieEntityDataStore dataStore;
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

    MovieEntityCloudDataStore getCloudDataStore() {
        return cloudBadgeDataStore;
    }

    MovieEntityDiskDataStore noteDiskDataStore() {
        return badgeCacheInDisk;
    }


}
