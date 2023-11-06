package com.vd.university.presentation.screen.client.home;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.screen.Screen;
import com.vd.university.presentation.screen.client.book.BookRoomScreen;
import com.vd.university.presentation.screen.client.empty_rooms.EmptyRoomsScreen;
import com.vd.university.util.log.Log;

public class ClientHomeScreen extends Screen {

    private final ApplicationComponent applicationComponent;

    private final Log log = Log.getInstance();

    private ClientHomeScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    @Override
    public void onCreate() {
        System.out.println(
                """
                        
                        
                        Hello! We are glad to welcome you to our hotel.
                        """
        );
    }

    @Override
    public void onDestroy() {
        System.out.println("Goodbye!");
    }

    public void showMenu() {
        System.out.println(
                """
                        How can I help you? (enter number)

                        1. I want to book a room!
                        2. I want to see available rooms!
                        3. Goodbye!
                        
                        """
        );
        System.out.print("Make your choice: ");
    }

    public void showBookRoomScreen() {
        BookRoomScreen bookRoomScreen = BookRoomScreen.getInstance(applicationComponent);

        log.info("BookRoomScreen was showed");
        bookRoomScreen.onCreate();

        log.info("BookRoomScreen was hided");
        bookRoomScreen.onDestroy();
    }

    public void showEmptyRoomsScreen() {
        EmptyRoomsScreen emptyRoomsScreen = EmptyRoomsScreen.getInstance(applicationComponent);

        log.info("EmptyRoomsScreen was showed");
        emptyRoomsScreen.onCreate();

        log.info("EmptyRoomsScreen was hided");
        emptyRoomsScreen.onDestroy();
    }

    public static ClientHomeScreen getInstance(ApplicationComponent applicationComponent) {
        return new ClientHomeScreen(applicationComponent);
    }
}