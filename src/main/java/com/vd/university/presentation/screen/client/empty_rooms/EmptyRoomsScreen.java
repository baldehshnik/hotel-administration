package com.vd.university.presentation.screen.client.empty_rooms;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.screen.Screen;

import java.util.List;

public class EmptyRoomsScreen extends Screen implements EmptyRoomsContract.View {

    private ApplicationComponent applicationComponent;

    private EmptyRoomsContract.Presenter presenter;

    private EmptyRoomsScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.emptyRoomPresenter(this);
        presenter.readEmptyRooms();
    }

    @Override
    public void onDestroy() {
        presenter = null;
        applicationComponent = null;
    }

    @Override
    public void onCorrectReading(List<RoomModel> rooms) {
        System.out.println();
        if (rooms.isEmpty()) {
            System.out.println("ALL ROOMS ARE FULL! We apologize for the inconvenience.\n");
            return;
        }

        String format = "| Number %4d | %4d beds | %9s |%n";

        System.out.format("+-------------+-----------+-----------+%n");
        System.out.format("|    Room     |   Beds    | Room type |%n");
        System.out.format("+-------------+-----------+-----------+%n");
        for (RoomModel room : rooms) {
            System.out.format(format, room.number(), room.bedsCount(), room.type());
        }
        System.out.format("+-------------+-----------+-----------+%n");
        System.out.println();
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    public static EmptyRoomsScreen getInstance(ApplicationComponent applicationComponent) {
        return new EmptyRoomsScreen(applicationComponent);
    }
}