package com.vd.university.presentation.screen.client.book;

import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.entity.RoomType;
import com.vd.university.presentation.mapper.ClientModelMapper;
import com.vd.university.presentation.mapper.OrderModelMapper;
import com.vd.university.presentation.repository.IClientRepository;
import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;

import java.util.List;

public class BookRoomPresenter implements BookRoomContract.Presenter {

    private IClientRepository repository;

    private BookRoomContract.View screen;

    private final ClientModelMapper clientModelMapper;
    private final OrderModelMapper orderModelMapper;

    public BookRoomPresenter(
            BookRoomContract.View screen,
            IClientRepository repository,
            ClientModelMapper clientModelMapper,
            OrderModelMapper orderModelMapper
    ) {
        this.repository = repository;
        this.screen = screen;
        this.clientModelMapper = clientModelMapper;
        this.orderModelMapper = orderModelMapper;
    }

    @Override
    public void readAvailableRooms(int bedsCount, RoomType roomType) {
        try {
            List<RoomModel> emptyRooms = repository.readEmptyRooms();
            for (RoomModel room : emptyRooms) {
                if (room.bedsCount() == bedsCount && room.type().name().equals(roomType.name())) {
                    screen.handleAvailableRoomReading(room);
                    return;
                }
            }

            screen.handleAvailableRoomReading(null);
        } catch (FailedReadException e) {
            screen.onError("Failed to read data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void registerClient(ClientModel client) {
        try {
            repository.registerClient(clientModelMapper.map(client));
        } catch (FailedInsertionException e) {
            screen.onError("Failed registration, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void readRegisteredClient(String passport, RoomModel room) {
        try {
            ClientModel client = repository.readClientByPassport(passport);
            screen.handleRegisteredClient(client, room);
        } catch (FailedReadException e) {
            screen.onError("Failed data reading, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        }
    }

    @Override
    public void createOrder(OrderModel order) {
        try {
            boolean response = repository.createOrder(orderModelMapper.map(order));
            if (!response) throw new FailedInsertionException("The order was not registered", this.getClass());
            else screen.handleOrderRegistration(order.room());
        } catch (FailedInsertionException e) {
            screen.onError("Failed order registration, try again later!\n The funds have been returned to your card!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!\n The funds have been returned to your card!");
        }
    }

    @Override
    public void onDestroy() {
        repository = null;
        screen = null;
    }
}