package com.vd.university.presentation.screen.activity;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.application.Context;
import com.vd.university.presentation.screen.admin.home.AdminHomeScreen;
import com.vd.university.presentation.screen.client.home.ClientHomeScreen;
import com.vd.university.util.log.Log;
import com.vd.university.util.model.Terminal;

import java.io.IOException;
import java.util.Scanner;

public class Activity extends Context implements HotelContract.View {

    private final IncorrectChoice incorrectChoice = () -> System.out.println("Sorry, but we can't help you with that!");

    private ApplicationComponent applicationComponent;

    private HotelContract.Presenter presenter;

    private Scanner scanner;

    private Log log;

    @Override
    public void onCreate() {
        this.applicationComponent = getApplication().getApplicationComponent();
        this.presenter = applicationComponent.hotelPresenter(this);

        try {
            Log.init();
            log = Log.getInstance();
            log.info("Application started");
        } catch (IOException e) {
            System.out.println("The program crashed!");
            return;
        }

        presenter.initDatabase();
    }

    private void onDestroy() {
        presenter.onDestroy();
        log.info("Application finished");
        log.close();
    }

    private void selectProgramUser() {
        try {
            System.out.println("""
                    
                    
                    Choose who you are?
                    
                    1. I am a client!
                    2. I am an administrator!
                    3. Cancel!
                    """);

            System.out.print("Please, make your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                log.info("The user has selected client application mode");
                runClientMenu();
            } else if (choice == 2) {
                scanner.nextLine();
                log.info("The user has selected application mode for administrator");

                boolean isPasswordCorrect = isPasswordCorrect();
                if (!isPasswordCorrect) {
                    log.warning("The user entered incorrect password", this.getClass());
                    System.out.println("\nPassword is incorrect\n");
                    return;
                }

                runAdminMenu();
            } else if (choice != 3) {
                throw new Exception();
            }
        } catch (Exception exception) {
            scanner.nextLine();
            incorrectChoice.incorrect();
            selectProgramUser();
        }
    }

    private void runAdminMenu() {
        AdminHomeScreen adminHomeScreen = AdminHomeScreen.getInstance(applicationComponent);
        adminHomeScreen.onCreate();
        while (true) {
            adminHomeScreen.showMenu();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        adminHomeScreen.showCurrentOrdersScreen();
                        break;
                    case 2:
                        adminHomeScreen.showViewClientsScreen();
                        break;
                    case 3:
                        scanner.nextLine();
                        adminHomeScreen.showCancelOrderScreen();
                        break;
                    case 4:
                        scanner.nextLine();
                        adminHomeScreen.showUpdateScreen();
                        break;
                    case 5:
                        adminHomeScreen.createReport();
                        break;
                    case 6:
                        adminHomeScreen.onDestroy();
                        return;
                    default:
                        throw new Exception();
                }
            } catch (Exception exception) {
                log.warning(
                        "The user entered an incorrect value in administrator menu and the application crashed",
                        this.getClass()
                );

                incorrectChoice.incorrect();
                adminHomeScreen.onDestroy();
                return;
            }
        }
    }

    private boolean isPasswordCorrect() {
        try {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            return password.equals(Terminal.ADMINISTRATOR_PASSWORD);
        } catch (Exception exception) {
            return false;
        }
    }

    private void runClientMenu() {
        ClientHomeScreen clientHomeScreen = ClientHomeScreen.getInstance(applicationComponent);
        clientHomeScreen.onCreate();
        while (true) {
            clientHomeScreen.showMenu();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        clientHomeScreen.showBookRoomScreen();
                        break;
                    case 2:
                        clientHomeScreen.showEmptyRoomsScreen();
                        break;
                    case 3:
                        clientHomeScreen.onDestroy();
                        return;
                    default:
                        throw new Exception();
                }
            } catch (Exception exception) {
                log.warning(
                        "The user entered an incorrect value in client menu and the application crashed",
                        this.getClass()
                );

                incorrectChoice.incorrect();
                clientHomeScreen.onDestroy();
                return;
            }
        }
    }

    @Override
    public void handleCorrectDatabaseInitialize() {
        this.scanner = applicationComponent.scanner();
        selectProgramUser();

        onDestroy();
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
        onDestroy();
    }

    private interface IncorrectChoice {
        void incorrect();
    }
}