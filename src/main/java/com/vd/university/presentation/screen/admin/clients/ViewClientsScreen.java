package com.vd.university.presentation.screen.admin.clients;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.screen.Screen;

import java.util.List;

public class ViewClientsScreen extends Screen implements ViewClientsContract.View {

    private final ApplicationComponent applicationComponent;
    private ViewClientsContract.Presenter presenter;

    private ViewClientsScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.viewClientsPresenter(this);
        presenter.readClients();
    }

    @Override
    public void handleClientsReadingResult(List<ClientModel> clients) {
        System.out.println();
        if (clients.isEmpty()) {
            System.out.println("No clients found!");
            return;
        }

        String format = "| %-10s | %-14s | %-12s | %9s |%n";

        System.out.format("+------------+----------------+--------------+-----------+%n");
        System.out.format("| Firstname  | Lastname       | Patronymic   | Passport  |%n");
        System.out.format("+------------+----------------+--------------+-----------+%n");
        for (ClientModel client : clients) {
            System.out.format(
                    format,
                    getCorrectDisplayString(client.firstname(), 10),
                    getCorrectDisplayString(client.lastname(), 14),
                    getCorrectDisplayString(client.patronymic(), 12),
                    getCorrectDisplayString(client.passport(), 9)
            );
        }
        System.out.format("+------------+----------------+--------------+-----------+%n");
        System.out.println("\n");
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
    }

    public static ViewClientsScreen getInstance(ApplicationComponent applicationComponent) {
        return new ViewClientsScreen(applicationComponent);
    }
}