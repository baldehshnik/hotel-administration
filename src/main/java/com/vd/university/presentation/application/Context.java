package com.vd.university.presentation.application;

public abstract class Context {

    private final Application application;

    protected Context() {
        this.application = new Application();
    }

    public Application getApplication() {
        return application;
    }

    public abstract void onCreate();
}