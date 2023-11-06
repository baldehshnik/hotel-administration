package com.vd.university.presentation.screen.admin.update;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.entity.RoomType;
import com.vd.university.presentation.screen.Screen;
import com.vd.university.presentation.reader.PassportReader;
import com.vd.university.presentation.reader.RoomReader;

import java.util.Scanner;

public class UpdateScreen extends Screen implements UpdateContract.View {

    private final ApplicationComponent applicationComponent;
    private final Scanner scanner;

    private UpdateContract.Presenter presenter;

    private final PassportReader passportReader;
    private final RoomReader roomReader;

    private UpdateScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        this.scanner = applicationComponent.scanner();
        this.passportReader = applicationComponent.dataReader();
        this.roomReader = applicationComponent.roomReader();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
    }

    @Override
    public void showToast(String message) {
        System.out.println(message);
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.updatePresenter(this);
        showMenu();
    }

    private void showMenu() {
        while (true) {
            try {
                System.out.println(
                        """
                                                            
                                Select the operation to perform!
                                                            
                                1. Add new room
                                2. Delete room
                                3. Delete client
                                4. Exit
                                                            
                                """
                );
                System.out.print("Make your choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        createRoom();
                        break;
                    case 2:
                        scanner.nextLine();
                        deleteRoom();
                        break;
                    case 3:
                        scanner.nextLine();
                        deleteClient();
                        break;
                    case 4:
                        break;
                    default:
                        throw new Exception();
                }

                return;
            } catch (Exception exception) {
                scanner.nextLine();
                System.out.println("The selected item does not exist, try again!");
                showMenu();
            }
        }
    }

    private void createRoom() {
        try {
            RoomType roomType = roomReader.readRoomType();
            int bedsCount = roomReader.readBedsCount();

            System.out.print("Enter the room number: ");
            int roomNumber = Integer.parseInt(scanner.nextLine());

            presenter.createRoom(new RoomModel(roomNumber, bedsCount, roomType));
        } catch (Exception exception) {
            System.out.println("You entered incorrect data! Try again!");
            createRoom();
        }
    }

    private void deleteClient() {
        try {
            System.out.print("Enter client passport to delete!\n");
            String passport = passportReader.readPassportSeries() + passportReader.readPassportNumber();
            presenter.deleteClient(passport);
        } catch (Exception exception) {
            System.out.println("You entered incorrect data! Try again!");
            deleteClient();
        }
    }

    private void deleteRoom() {
        try {
            System.out.print("Enter the room number to delete: ");
            int roomNumber = Integer.parseInt(scanner.nextLine());
            presenter.deleteRoom(roomNumber);
        } catch (Exception exception) {
            System.out.println("You entered incorrect data!");
            deleteRoom();
        }
    }

    public static UpdateScreen getInstance(ApplicationComponent applicationComponent) {
        return new UpdateScreen(applicationComponent);
    }
}