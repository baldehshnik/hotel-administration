package com.vd.university.presentation.screen.client.empty_rooms;

import com.vd.university.presentation.entity.RoomModel;

import java.util.List;

public interface EmptyRoomsContract {
    interface View {
        void onCorrectReading(List<RoomModel> rooms);
        void onError(String message);
    }

    interface Presenter {

        void readEmptyRooms();

        void onDestroy();
    }
}