package com.vd.university.presentation.screen.client.book;

import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.entity.RoomType;

public interface BookRoomContract {

    interface View {

        void handleRegisteredClient(ClientModel client, RoomModel room);

        void handleOrderRegistration(RoomModel room);

        void handleAvailableRoomReading(RoomModel room);

        void onError(String message);

    }

    interface Presenter {

        void readRegisteredClient(String passport, RoomModel room);

        void createOrder(OrderModel order);

        void readAvailableRooms(int bedsCount, RoomType roomType);

        void registerClient(ClientModel client);

        void onDestroy();
    }
}