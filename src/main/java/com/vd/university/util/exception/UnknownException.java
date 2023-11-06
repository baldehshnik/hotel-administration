package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

public class UnknownException extends Exception {

    public UnknownException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}