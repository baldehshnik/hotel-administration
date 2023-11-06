package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

public class FailedReadException extends Exception {

    public FailedReadException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}