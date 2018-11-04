package com.tony.tang.note.domain.entity;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class ArsenalAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new com.tony.tang.note.domain.entity.AutoValueGson_ArsenalAdapterFactory();
    }

}