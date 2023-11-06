package com.vd.university.di.module;

import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.presentation.mapper.ClientModelMapper;
import com.vd.university.presentation.mapper.OrderModelMapper;
import com.vd.university.presentation.mapper.RoomModelMapper;
import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.presentation.repository.IDatabaseInitRepository;
import com.vd.university.presentation.screen.activity.HotelContract;
import com.vd.university.presentation.screen.activity.HotelPresenter;
import com.vd.university.presentation.screen.admin.cancel_order.CancelOrderContract;
import com.vd.university.presentation.screen.admin.cancel_order.CancelOrderPresenter;
import com.vd.university.presentation.screen.admin.clients.ViewClientsContract;
import com.vd.university.presentation.screen.admin.clients.ViewClientsPresenter;
import com.vd.university.presentation.screen.admin.home.AdminHomeContract;
import com.vd.university.presentation.screen.admin.home.AdminHomePresenter;
import com.vd.university.presentation.screen.admin.orders.CurrentOrdersContract;
import com.vd.university.presentation.screen.admin.orders.CurrentOrdersPresenter;
import com.vd.university.presentation.screen.admin.update.UpdateContract;
import com.vd.university.presentation.screen.admin.update.UpdatePresenter;
import com.vd.university.presentation.screen.client.book.BookRoomPresenter;
import com.vd.university.presentation.screen.client.empty_rooms.EmptyRoomPresenter;
import com.vd.university.presentation.repository.IClientRepository;
import com.vd.university.presentation.screen.client.book.BookRoomContract;
import com.vd.university.presentation.screen.client.empty_rooms.EmptyRoomsContract;
import com.vd.university.presentation.reader.PassportReader;
import com.vd.university.presentation.reader.RoomReader;

import java.util.Scanner;

@SingletonScope
public class PresentationModule {

    private final IClientRepository hotelRepository;
    private final IAdministratorRepository administratorRepository;
    private final IDatabaseInitRepository databaseInitRepository;

    private final Scanner scanner;

    public PresentationModule(
            IClientRepository hotelRepository,
            IAdministratorRepository administratorRepository,
            IDatabaseInitRepository databaseInitRepository,
            Scanner scanner
    ) {
        this.hotelRepository = hotelRepository;
        this.administratorRepository = administratorRepository;
        this.databaseInitRepository = databaseInitRepository;
        this.scanner = scanner;
    }

    public HotelContract.Presenter getHotelPresenter(HotelContract.View view) {
        return new HotelPresenter(databaseInitRepository, view);
    }

    public EmptyRoomsContract.Presenter getEmptyRoomPresenter(EmptyRoomsContract.View screen) {
        return new EmptyRoomPresenter(screen, hotelRepository);
    }

    public BookRoomContract.Presenter getBookRoomPresenter(BookRoomContract.View screen) {
        return new BookRoomPresenter(screen, hotelRepository, getClientModelMapper(), getOrderModelMapper());
    }

    public ViewClientsContract.Presenter getViewClientsPresenter(ViewClientsContract.View screen) {
        return new ViewClientsPresenter(screen, administratorRepository);
    }

    public CurrentOrdersContract.Presenter getCurrentOrdersPresenter(CurrentOrdersContract.View screen) {
        return new CurrentOrdersPresenter(screen, administratorRepository);
    }

    public UpdateContract.Presenter getUpdatePresenter(UpdateContract.View screen) {
        return new UpdatePresenter(administratorRepository, screen, getRoomModelMapper());
    }

    public CancelOrderContract.Presenter getCancelOrderPresenter(CancelOrderContract.View screen) {
        return new CancelOrderPresenter(administratorRepository, screen);
    }

    public AdminHomeContract.Presenter getAdminHomePresenter(AdminHomeContract.View screen) {
        return new AdminHomePresenter(administratorRepository, screen);
    }

    public PassportReader getDataReader() {
        return new PassportReader(scanner);
    }

    public RoomReader getRoomReader() {
        return new RoomReader(scanner);
    }

    public ClientModelMapper getClientModelMapper() {
        return new ClientModelMapper();
    }

    public RoomModelMapper getRoomModelMapper() {
        return new RoomModelMapper();
    }

    public OrderModelMapper getOrderModelMapper() {
        return new OrderModelMapper(getClientModelMapper(), getRoomModelMapper());
    }
}