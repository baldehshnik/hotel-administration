package com.vd.university.presentation.screen.client.book;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.entity.RoomType;
import com.vd.university.presentation.screen.Screen;
import com.vd.university.presentation.reader.PassportReader;
import com.vd.university.presentation.reader.RoomReader;
import com.vd.university.util.model.Terminal;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class BookRoomScreen extends Screen implements BookRoomContract.View {

    private final ApplicationComponent applicationComponent;

    private BookRoomContract.Presenter presenter;

    private final Scanner scanner;

    private final PassportReader passportReader;
    private final RoomReader roomReader;

    private boolean isError = false;

    private BookRoomScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        scanner = applicationComponent.scanner();
        passportReader = applicationComponent.dataReader();
        roomReader = applicationComponent.roomReader();
    }

    public static BookRoomScreen getInstance(ApplicationComponent applicationComponent) {
        return new BookRoomScreen(applicationComponent);
    }

    @Override
    public void onCreate() {
        presenter = applicationComponent.bookRoomPresenter(this);
        run();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
    }

    @Override
    public void handleRegisteredClient(ClientModel client, RoomModel room) {
        if (client == null) {
            System.out.println("\nSorry, but we could not find you!\n");
            readPersonalData(room);
        } else {
            System.out.println("Hello, " + client.firstname() + "! Let's finish booking.");
            createOrder(client, room);
        }
    }

    @Override
    public void handleOrderRegistration(RoomModel room) {
        System.out.println("\nBooking has been confirmed!");
        System.out.println("Your room " + room.number() + " is located on floor number " + room.number() / 100 + "\n\n");
    }

    @Override
    public void handleAvailableRoomReading(RoomModel room) {
        if (room == null) {
            System.out.println("\nSorry, but there are no rooms available at the moment!\n");
            return;
        }

        System.out.println("\nWe found a room for you! Now, we need your personal data!");

        boolean response = isClientOfHotel();
        if (response) {
            String passport = passportReader.readPassportSeries() + passportReader.readPassportNumber();
            presenter.readRegisteredClient(passport, room);
            return;
        }

        readPersonalData(room);
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
        isError = true;
    }

    private void readPersonalData(RoomModel room) {
        String firstname = readFirstname();
        String lastname = readLastname();
        String patronymic = readPatronymic();
        String passport = passportReader.readPassportSeries() + passportReader.readPassportNumber();

        ClientModel client = new ClientModel(firstname, lastname, patronymic, passport);
        presenter.registerClient(client);

        if (isError) return;

        createOrder(client, room);
    }

    private boolean isClientOfHotel() {
        try {
            System.out.println("Are you a client of our hotel?");
            System.out.print("Please enter \"yes\" or \"no\": ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) return true;
            else if (answer.equalsIgnoreCase("no")) return false;
            else throw new Exception();
        } catch (Exception exception) {
            System.out.println("You entered incorrect value! Try again.");
            return isClientOfHotel();
        }
    }

    private void run() {
        int bedsCount = roomReader.readBedsCount();
        RoomType roomType = roomReader.readRoomType();

        if (isDataCorrect(bedsCount, roomType)) {
            presenter.readAvailableRooms(bedsCount, roomType);
        } else {
            System.out.println("Okay, try again!");
            run();
        }
    }

    private void createOrder(ClientModel client, RoomModel room) {
        try {
            long daysCount = readDaysCount();
            long time = System.currentTimeMillis() / 1000 + daysCount * 24 * 3600;
            OrderModel order = new OrderModel(client, time, room);

            double price = switch (room.type()) {
                case VIP ->
                        daysCount * Terminal.VIP_ROOM_PRICE + Terminal.VIP_ROOM_PRICE / (10 - room.bedsCount()) * daysCount;
                case CHILDREN ->
                        daysCount * Terminal.CHILDREN_ROOM_PRICE + Terminal.CHILDREN_ROOM_PRICE / (10 - room.bedsCount()) * daysCount;
                default ->
                        daysCount * Terminal.STANDARD_ROOM_PRICE + Terminal.STANDARD_ROOM_PRICE / (10 - room.bedsCount()) * daysCount;
            };

            String priceOutput = new DecimalFormat("#.##").format(price);
            System.out.println("\nPlease pay for your reservation (" + priceOutput + " BYN)!\n");
            payForTheRoom();

            presenter.createOrder(order);
        } catch (Exception exception) {
            System.out.println("An error occurred, try choosing a shorter booking period!");
            createOrder(client, room);
        }
    }

    private void payForTheRoom() {
        System.out.print("Enter your card number: ");
        try {
            String cardNumber = scanner.nextLine().replaceAll(" ", "");
            Long.parseLong(cardNumber);
            if (cardNumber.length() != 16) {
                throw new Exception();
            }

            confirmPayment();
        } catch (Exception exception) {
            System.out.println("The card number is incorrect, try again!");
            payForTheRoom();
        }
    }

    private void confirmPayment() {
        try {
            int verifyCode = new Random().nextInt(900) + 100;
            System.out.println("Your verify code is " + verifyCode);
            System.out.print("To confirm payment, enter the code shown on the screen: ");
            int enteredCode = scanner.nextInt();

            if (verifyCode == enteredCode) {
                System.out.println("Payment completed!");
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            scanner.nextLine();
            System.out.println("The entered code does not match. Try again!");
            confirmPayment();
        }
    }

    private boolean isDataCorrect(int bedsCount, RoomType roomType) {
        String format = "| %4d beds | %9s |%n";

        System.out.format("+-----------+-----------+%n");
        System.out.format("|   Beds    | Room type |%n");
        System.out.format("+-----------+-----------+%n");
        System.out.format(format, bedsCount, roomType.name());
        System.out.format("+-----------+-----------+%n");

        while (true) {
            System.out.print("\nAre the entered data correct? Please, enter \"yes\" or \"no\": ");
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.println("Sorry, but you entered incorrect value. Try again, please!");
            }
        }
    }

    private String readPatronymic() {
        System.out.print("Enter your patronymic: ");
        try {
            String patronymic = scanner.nextLine();
            if (!patronymic.isEmpty() && passportReader.notContainsDigits(patronymic)) {
                return patronymic;
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but you must enter patronymic!");
        }

        return readPatronymic();
    }

    private String readLastname() {
        System.out.print("Enter your lastname: ");
        try {
            String lastname = scanner.nextLine();
            if (!lastname.isEmpty() && passportReader.notContainsDigits(lastname)) {
                return lastname;
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but you must enter lastname!");
        }

        return readLastname();
    }

    private String readFirstname() {
        System.out.print("Enter your firstname: ");
        try {
            String firstname = scanner.nextLine();
            if (!firstname.isEmpty() && passportReader.notContainsDigits(firstname)) {
                return firstname;
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but you must enter firstname. Try again!");
        }

        return readFirstname();
    }

    private int readDaysCount() {
        System.out.print("Please enter how many days you would like to book a room for: ");
        try {
            int days = scanner.nextInt();
            if (days > 0 && days < 100) {
                scanner.nextLine();
                return days;
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            scanner.nextLine();
            System.out.println("Sorry. The value you entered is incorrect. Try again!");
        }

        return readDaysCount();
    }
}