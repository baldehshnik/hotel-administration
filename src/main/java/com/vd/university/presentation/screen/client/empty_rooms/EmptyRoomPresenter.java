package com.vd.university.presentation.screen.client.empty_rooms;

import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.repository.IClientRepository;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;

import java.util.List;

public class EmptyRoomPresenter implements EmptyRoomsContract.Presenter {

    private IClientRepository repository;

    private EmptyRoomsContract.View screen;

    public EmptyRoomPresenter(EmptyRoomsContract.View screen, IClientRepository repository) {
        this.screen = screen;
        this.repository = repository;
    }

    @Override
    public void readEmptyRooms() {
        try {
            List<RoomModel> rooms = repository.readEmptyRooms();
            screen.onCorrectReading(rooms);
        } catch (FailedReadException e) {
            screen.onError("Failed to receive data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void onDestroy() {
        repository = null;
        screen = null;
    }
}