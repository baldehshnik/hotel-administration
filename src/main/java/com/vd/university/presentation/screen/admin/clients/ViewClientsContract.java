package com.vd.university.presentation.screen.admin.clients;

import com.vd.university.presentation.entity.ClientModel;

import java.util.List;

public interface ViewClientsContract {

    interface View {

        void handleClientsReadingResult(List<ClientModel> clients);

        void onError(String message);

    }

    interface Presenter {

        void readClients();

        void onDestroy();
    }
}