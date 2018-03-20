package com.jordifierro.androidbase.domain.cache;

/**
 * Created by jordifierro on 18/12/16.
 */

public interface UserCache {

    void save(String userJsonString);

    void remove();

    String get();
}
