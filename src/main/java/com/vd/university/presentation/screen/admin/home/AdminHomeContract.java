package com.vd.university.presentation.screen.admin.home;

public interface AdminHomeContract {

    interface View {

        void handleCorrectReportCreation();

        void createReport();

        void onError(String message);
    }

    interface Presenter {

        void createReport();

        void onDestroy();
    }
}