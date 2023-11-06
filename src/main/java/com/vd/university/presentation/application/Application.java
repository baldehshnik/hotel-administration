package com.vd.university.presentation.application;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.screen.activity.Activity;

public class Application {

    private final ApplicationComponent applicationComponent = ApplicationComponent.getInstance();

    public static void main(String[] args) {
        Activity activity = new Activity();
        activity.onCreate();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}