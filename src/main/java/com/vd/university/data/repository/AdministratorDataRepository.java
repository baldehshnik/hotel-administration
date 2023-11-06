package com.vd.university.data.repository;

import com.vd.university.data.dao.client.IClientDao;
import com.vd.university.data.dao.order.IOrderDao;
import com.vd.university.data.dao.room.IRoomDao;
import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;
import com.vd.university.data.mapper.ClientEntityMapper;
import com.vd.university.data.mapper.OrderEntityMapper;
import com.vd.university.data.source.ReportDataSource;
import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SingletonScope
public class AdministratorDataRepository implements IAdministratorRepository {

    private final IClientDao clientDao;
    private final IOrderDao orderDao;
    private final IRoomDao roomDao;

    private final ClientEntityMapper clientEntityMapper;
    private final OrderEntityMapper orderEntityMapper;

    public AdministratorDataRepository(
            IClientDao clientDao,
            IOrderDao orderDao,
            IRoomDao roomDao,
            ClientEntityMapper clientEntityMapper,
            OrderEntityMapper orderEntityMapper
    ) {
        this.clientDao = clientDao;
        this.orderDao = orderDao;
        this.roomDao = roomDao;
        this.clientEntityMapper = clientEntityMapper;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public void createReport() throws FailedReadException, ReportFileUsageException, UnknownException {
        try {
            ReportDataSource reportDataSource = new ReportDataSource();

            List<RoomEntity> rooms = roomDao.readAllRooms();
            List<ClientEntity> clients = clientDao.readClients();
            List<OrderEntity> orders = orderDao.readOrders();

            reportDataSource.writeRoomsInfo(rooms);
            reportDataSource.writeClientsInfo(clients);
            reportDataSource.writeOrdersInfo(orders);
        } catch (SQLException exception) {
            throw new FailedReadException(exception.getMessage(), this.getClass());
        } catch (IOException exception) {
            throw new ReportFileUsageException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public boolean deleteClientByPassportIfNotBookingRoom(String passport) throws FailedDeletionException, ClientCurrentlyInHotelException, UnknownException {
        try {
            ClientEntity client = clientDao.readClientByPassport(passport);
            OrderEntity order = orderDao.readOrderByClientId(client);

            if (order == null) {
                int response = clientDao.delete(passport);
                return response > 0;
            }
        } catch (SQLException exception) {
            throw new FailedDeletionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }

        throw new ClientCurrentlyInHotelException();
    }

    @Override
    public boolean createRoom(RoomEntity room) throws FailedInsertionException, UnknownException {
        try {
            int response = roomDao.create(room);
            return response > 0;
        } catch (SQLException exception) {
            throw new FailedInsertionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public List<ClientModel> readClients() throws FailedReadException, UnknownException {
        try {
            List<ClientEntity> clientEntities = clientDao.readClients();
            List<ClientModel> clientModels = new ArrayList<>(clientEntities.size());

            for (ClientEntity clientEntity : clientEntities) {
                clientModels.add(clientEntityMapper.map(clientEntity));
            }

            return clientModels;
        } catch (SQLException exception) {
            throw new FailedReadException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public List<OrderModel> readCurrentOrders() throws FailedReadException, UnknownException {
        try {
            List<OrderEntity> orderEntities = orderDao.readOrders();
            List<OrderModel> orderModels = new ArrayList<>(orderEntities.size());

            for (OrderEntity orderEntity : orderEntities) {
                orderModels.add(orderEntityMapper.map(orderEntity));
            }

            return orderModels;
        } catch (SQLException exception) {
            throw new FailedReadException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public boolean cancelOrderByRoomNumber(int roomNumber) throws FailedDeletionException, UnknownException {
        try {
            int isDeleted = orderDao.cancelOrderByRoomNumber(roomNumber, roomDao.getDaoBuilder());
            return isDeleted > 0;
        } catch (SQLException exception) {
            throw new FailedDeletionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public boolean deleteRoomByRoomNumberIfNotReserved(int roomNumber) throws FailedDeletionException, RoomReservedException, UnknownException {
        try {
            RoomEntity room = roomDao.readRoomByNumber(roomNumber);
            OrderEntity order = orderDao.readOrderByRoomId(room);

            if (order == null) {
                int response = roomDao.delete(roomNumber);
                return response > 0;
            }
        } catch (SQLException exception) {
            throw new FailedDeletionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }

        throw new RoomReservedException();
    }
}