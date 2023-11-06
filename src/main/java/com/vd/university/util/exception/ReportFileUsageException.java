package com.vd.university.util.exception;

import com.vd.university.util.log.Log;

import java.io.IOException;

public class ReportFileUsageException extends IOException {

    public ReportFileUsageException(String message, Class<?> clazz) {
        Log.getInstance().error(message, clazz);
    }
}