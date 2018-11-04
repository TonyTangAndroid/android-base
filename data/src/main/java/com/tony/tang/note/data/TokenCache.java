package com.tony.tang.note.data;

/**
 * Created by jordifierro on 18/12/16.
 */

public interface TokenCache {

    void save(String token);

    void remove();

    String get();
}
