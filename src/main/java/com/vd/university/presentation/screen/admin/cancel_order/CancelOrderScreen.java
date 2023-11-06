package com.vd.university.presentation.screen.admin.cancel_order;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.screen.Screen;
import com.vd.university.util.model.Terminal;

import java.util.Scanner;

public class CancelOrderScreen extends Screen implements CancelOrderContract.View {

    private final ApplicationComponent applicationComponent;
    private final Scanner scanner;

    private CancelOrderContract.Presenter presenter;

    private CancelOrderScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        this.scanner = applicationComponent.scanner();
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.cancelOrderPresenter(this);
        run();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
    }

    @Override
    public void handleCorrectCancel() {
        System.out.println("Request was finished successfully!");
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    private void run() {
        try {
            System.out.print("Enter room number to cancel reservation (write \"CANCEL\" to return back): ");
            String roomNumberStr = scanner.nextLine();

            if (roomNumberStr.equals("CANCEL")) {
                System.out.println("Canceling an operation!\n");
                return;
            }

            int roomNumber = Integer.parseInt(roomNumberStr);
            if (
                    roomNumberStr.length() != 3 || roomNumber / 100 <= 1 ||
                    roomNumber / 100 > Terminal.FLOORS_COUNT ||
                    Integer.parseInt(roomNumberStr.substring(1)) > Terminal.MAX_ROOMS_ON_THE_FLOOR
            ) {
                throw new Exception();
            } else {
                presenter.cancelOrderByRoomNumber(roomNumber);
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but data is incorrect! Try again.");
            run();
        }
    }

    public static CancelOrderScreen getInstance(ApplicationComponent applicationComponent) {
        return new CancelOrderScreen(applicationComponent);
    }
}