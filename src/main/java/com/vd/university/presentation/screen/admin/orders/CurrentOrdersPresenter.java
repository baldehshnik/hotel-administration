package com.vd.university.presentation.screen.admin.orders;

import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;

public class CurrentOrdersPresenter implements CurrentOrdersContract.Presenter {

    private CurrentOrdersContract.View screen;

    private final IAdministratorRepository repository;

    public CurrentOrdersPresenter(CurrentOrdersContract.View screen, IAdministratorRepository repository) {
        this.screen = screen;
        this.repository = repository;
    }

    @Override
    public void readCurrentOrders() {
        try {
            screen.handleCurrentOrdersReading(repository.readCurrentOrders());
        } catch (FailedReadException e) {
            screen.onError("Failed to receive data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void onDestroy() {
        screen = null;
    }
}