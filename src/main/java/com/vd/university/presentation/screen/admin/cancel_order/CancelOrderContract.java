package com.vd.university.presentation.screen.admin.cancel_order;

public interface CancelOrderContract {

    interface View {

        void handleCorrectCancel();

        void onError(String message);
    }

    interface Presenter {

        void cancelOrderByRoomNumber(int roomNumber);

        void onDestroy();
    }
}