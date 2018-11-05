package com.tony.tang.movie;

import android.annotation.SuppressLint;
import android.util.Log;

import javax.annotation.Nonnull;

import timber.log.Timber;

@SuppressLint("LogNotTimber")
public class TimberLogUtil {

    public static void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private static void error(String tag, @Nonnull String message, Throwable t) {
        Log.e(tag, message);

    }

    private static void logWarning(String tag, @Nonnull String message) {
        Log.w(tag, message);
    }

    public static class ReleaseTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, @Nonnull String message, Throwable t) {
            switch (priority) {
                case Log.ERROR:
                    error(tag, message, t);
                    break;
                case Log.WARN:
                    logWarning(tag, message);
                    break;
                default:
                    break;
            }
        }
    }

}
