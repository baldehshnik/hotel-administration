package com.vd.university.presentation.screen.admin.update;

import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.mapper.RoomModelMapper;
import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.*;

public class UpdatePresenter implements UpdateContract.Presenter {

    private final IAdministratorRepository repository;

    private UpdateContract.View screen;

    private final RoomModelMapper roomModelMapper;

    public UpdatePresenter(IAdministratorRepository repository, UpdateContract.View screen, RoomModelMapper roomModelMapper) {
        this.repository = repository;
        this.screen = screen;
        this.roomModelMapper = roomModelMapper;
    }

    @Override
    public void createRoom(RoomModel room) {
        try {
            boolean response = repository.createRoom(roomModelMapper.map(room));
            if (response) {
                screen.showToast("The room creation was finished successfully!");
            } else {
                screen.onError("The room recording was not performed for an unknown reason");
            }
        } catch (FailedInsertionException e) {
            screen.onError("Failed to insert data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void deleteClient(String passport) {
        try {
            boolean response = repository.deleteClientByPassportIfNotBookingRoom(passport);
            if (response) {
                screen.showToast("The client deletion was finished successfully!");
            } else {
                screen.onError("The client was not found!");
            }
        } catch (FailedDeletionException e) {
            screen.onError("Failed to delete data, try again later!");
        } catch (ClientCurrentlyInHotelException e) {
            screen.onError("The client currently lives at the hotel");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void deleteRoom(int roomNumber) {
        try {
            boolean response = repository.deleteRoomByRoomNumberIfNotReserved(roomNumber);
            if (response) {
                screen.showToast("The room deletion was finished successfully!");
            } else {
                screen.onError("The room was not found!");
            }
        } catch (FailedDeletionException e) {
            screen.onError("Failed to delete data, try again later!");
        } catch (RoomReservedException e) {
            screen.onError("Sorry, but this room is currently booked!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void onDestroy() {
        screen = null;
    }
}