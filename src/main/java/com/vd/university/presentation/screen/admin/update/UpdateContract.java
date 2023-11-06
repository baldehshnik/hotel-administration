package com.vd.university.presentation.screen.admin.update;

import com.vd.university.presentation.entity.RoomModel;

public interface UpdateContract {

    interface View {

        void showToast(String message);

        void onError(String message);
    }

    interface Presenter {

        void createRoom(RoomModel room);

        void deleteClient(String passport);

        void deleteRoom(int roomNumber);

        void onDestroy();
    }
}