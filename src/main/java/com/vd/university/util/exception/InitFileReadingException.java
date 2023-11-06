package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

public class InitFileReadingException extends Exception {

    public InitFileReadingException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}