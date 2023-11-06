package com.vd.university.presentation.screen.admin.cancel_order;

import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.FailedDeletionException;
import com.vd.university.util.exception.UnknownException;

public class CancelOrderPresenter implements CancelOrderContract.Presenter {

    private CancelOrderContract.View screen;

    private final IAdministratorRepository repository;

    public CancelOrderPresenter(IAdministratorRepository repository, CancelOrderContract.View screen) {
        this.repository = repository;
        this.screen = screen;
    }

    @Override
    public void cancelOrderByRoomNumber(int roomNumber) {
        try {
            boolean response = repository.cancelOrderByRoomNumber(roomNumber);
            if (response) screen.handleCorrectCancel();
            else screen.onError("The order was not found!");
        } catch (FailedDeletionException e) {
            screen.onError("Failed to delete order, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void onDestroy() {
        screen = null;
    }
}