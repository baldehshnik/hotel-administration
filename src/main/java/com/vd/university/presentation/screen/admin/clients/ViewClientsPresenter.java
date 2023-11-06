package com.vd.university.presentation.screen.admin.clients;

import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;

import java.util.List;

public class ViewClientsPresenter implements ViewClientsContract.Presenter {

    private ViewClientsContract.View screen;

    private final IAdministratorRepository repository;

    public ViewClientsPresenter(ViewClientsContract.View screen, IAdministratorRepository repository) {
        this.screen = screen;
        this.repository = repository;
    }

    @Override
    public void onDestroy() {
        screen = null;
    }

    @Override
    public void readClients() {
        try {
            List<ClientModel> clients = repository.readClients();
            screen.handleClientsReadingResult(clients);
        } catch (FailedReadException e) {
            screen.onError("Failed to receive data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }
}