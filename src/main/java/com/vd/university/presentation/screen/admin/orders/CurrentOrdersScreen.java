package com.vd.university.presentation.screen.admin.orders;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.screen.Screen;

import java.util.List;

public class CurrentOrdersScreen extends Screen implements CurrentOrdersContract.View {

    private final ApplicationComponent applicationComponent;
    private CurrentOrdersContract.Presenter presenter;

    private CurrentOrdersScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public static CurrentOrdersScreen getInstance(ApplicationComponent applicationComponent) {
        return new CurrentOrdersScreen(applicationComponent);
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.currentOrdersPresenter(this);
        presenter.readCurrentOrders();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
    }

    @Override
    public void handleCurrentOrdersReading(List<OrderModel> orders) {
        System.out.println();
        if (orders.isEmpty()) {
            System.out.println("No valid orders found!");
            return;
        }

        String format = "| %-38s | Number %4d | %9s |  %2d days  |%n";

        System.out.format("+----------------------------------------+-------------+-----------+-----------+%n");
        System.out.format("| Client full name                       |    Room     | Room type | Time left |%n");
        System.out.format("+----------------------------------------+-------------+-----------+-----------+%n");
        for (OrderModel order : orders) {
            ClientModel client = order.client();
            RoomModel room = order.room();
            System.out.format(
                    format,
                    getCorrectDisplayString(
                            client.firstname() + " " + client.lastname() + " " + client.patronymic(),
                            38
                    ),
                    room.number(),
                    room.type(),
                    order.time() / (3600 * 24) - System.currentTimeMillis() / (1000 * 3600 * 24)
            );
        }
        System.out.format("+----------------------------------------+-------------+-----------+-----------+%n");
        System.out.println("\n");
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }
}