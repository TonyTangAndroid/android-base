package com.tony.tang.note.db;

import java.util.Date;

import javax.annotation.Nonnull;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(@Nonnull Long value) {
        return new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(@Nonnull Date date) {
        return date.getTime();
    }
}