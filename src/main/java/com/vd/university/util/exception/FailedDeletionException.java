package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

public class FailedDeletionException extends Exception {

    public FailedDeletionException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}