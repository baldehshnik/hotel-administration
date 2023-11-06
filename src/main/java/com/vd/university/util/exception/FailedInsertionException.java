package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

public class FailedInsertionException extends Exception {

    public FailedInsertionException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}