package com.vd.university.presentation.screen.activity;

public interface HotelContract {

    interface View {

        void handleCorrectDatabaseInitialize();

        void onError(String message);
    }

    interface Presenter {

        void initDatabase();

        void onDestroy();
    }
}