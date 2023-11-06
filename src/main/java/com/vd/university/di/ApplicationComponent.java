package com.vd.university.di;

import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.di.module.DataModule;
import com.vd.university.di.module.PresentationModule;
import com.vd.university.di.module.ScannerModule;
import com.vd.university.presentation.screen.activity.HotelContract;
import com.vd.university.presentation.screen.admin.cancel_order.CancelOrderContract;
import com.vd.university.presentation.screen.admin.clients.ViewClientsContract;
import com.vd.university.presentation.screen.admin.home.AdminHomeContract;
import com.vd.university.presentation.screen.admin.orders.CurrentOrdersContract;
import com.vd.university.presentation.screen.admin.update.UpdateContract;
import com.vd.university.presentation.screen.client.book.BookRoomContract;
import com.vd.university.presentation.screen.client.empty_rooms.EmptyRoomsContract;
import com.vd.university.presentation.reader.PassportReader;
import com.vd.university.presentation.reader.RoomReader;

import java.util.Scanner;

@SingletonScope
public class ApplicationComponent {

    private static volatile ApplicationComponent component = null;

    private final PresentationModule presentationModule;
    private final ScannerModule scannerModule;

    private ApplicationComponent() {
        DataModule dataModule = new DataModule();
        this.scannerModule = new ScannerModule();
        this.presentationModule = new PresentationModule(
                dataModule.getClientRepository(),
                dataModule.getIAdministratorRepository(),
                dataModule.getDatabaseInitRepository(),
                scannerModule.getScanner()
        );
    }

    public PassportReader dataReader() {
        return presentationModule.getDataReader();
    }

    public RoomReader roomReader() {
        return presentationModule.getRoomReader();
    }

    public AdminHomeContract.Presenter adminHomePresenter(AdminHomeContract.View screen) {
        return presentationModule.getAdminHomePresenter(screen);
    }

    public HotelContract.Presenter hotelPresenter(HotelContract.View view) {
        return presentationModule.getHotelPresenter(view);
    }

    public BookRoomContract.Presenter bookRoomPresenter(BookRoomContract.View screen) {
        return presentationModule.getBookRoomPresenter(screen);
    }

    public EmptyRoomsContract.Presenter emptyRoomPresenter(EmptyRoomsContract.View screen) {
        return presentationModule.getEmptyRoomPresenter(screen);
    }

    public ViewClientsContract.Presenter viewClientsPresenter(ViewClientsContract.View screen) {
        return presentationModule.getViewClientsPresenter(screen);
    }

    public CancelOrderContract.Presenter cancelOrderPresenter(CancelOrderContract.View screen) {
        return presentationModule.getCancelOrderPresenter(screen);
    }

    public CurrentOrdersContract.Presenter currentOrdersPresenter(CurrentOrdersContract.View screen) {
        return presentationModule.getCurrentOrdersPresenter(screen);
    }

    public UpdateContract.Presenter updatePresenter(UpdateContract.View screen) {
        return presentationModule.getUpdatePresenter(screen);
    }

    public Scanner scanner() {
        return scannerModule.getScanner();
    }

    public static ApplicationComponent getInstance() {
        if (component == null) component = new ApplicationComponent();
        return component;
    }
}