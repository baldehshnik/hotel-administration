package com.vd.university.data.repository;

import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.dao.client.ClientDao;
import com.vd.university.data.dao.order.OrderDao;
import com.vd.university.data.dao.room.RoomDao;
import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;
import com.vd.university.data.mapper.ClientEntityMapper;
import com.vd.university.data.mapper.RoomEntityMapper;
import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.repository.IClientRepository;
import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;
import com.vd.university.util.log.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SingletonScope
public class ClientDataRepository implements IClientRepository {

    private final ClientDao clientDao;
    private final OrderDao orderDao;
    private final RoomDao roomDao;

    private final RoomEntityMapper roomEntityMapper;
    private final ClientEntityMapper clientEntityMapper;

    public ClientDataRepository(
            ClientDao IClientDao,
            OrderDao orderDao,
            RoomDao roomDao,
            RoomEntityMapper roomEntityMapper,
            ClientEntityMapper clientEntityMapper
    ) {
        this.clientDao = IClientDao;
        this.orderDao = orderDao;
        this.roomDao = roomDao;
        this.roomEntityMapper = roomEntityMapper;
        this.clientEntityMapper = clientEntityMapper;
    }

    @Override
    public List<RoomModel> readEmptyRooms() throws FailedReadException, UnknownException {
        try {
            updateRooms();

            QueryBuilder<OrderEntity, ?> subQuery = orderDao.readQueryToReadRoomsIds();
            List<RoomEntity> roomEntities = roomDao.readEmptyRooms(subQuery);
            List<RoomModel> roomModels = new ArrayList<>(roomEntities.size());

            for (RoomEntity room : roomEntities) roomModels.add(roomEntityMapper.map(room));
            return roomModels;
        } catch (SQLException exception) {
            throw new FailedReadException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public void registerClient(ClientEntity client) throws FailedInsertionException, UnknownException {
        try {
            ClientEntity registeredClient = clientDao.readClientById(client);
            if (registeredClient == null) {
                clientDao.registerClient(client);
            }
        } catch (SQLException exception) {
            throw new FailedInsertionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public boolean createOrder(OrderEntity order) throws FailedInsertionException, UnknownException {
        try {
            orderDao.createOrder(
              new OrderEntity(
                      clientDao.readClientByPassport(order.getClient().getPassport()),
                      order.getTime(),
                      roomDao.readRoomByNumber(order.getRoom().getNumber())
              )
            );

            return true;
        } catch (SQLException exception) {
            throw new FailedInsertionException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    @Override
    public ClientModel readClientByPassport(String passport) throws FailedReadException, UnknownException {
        try {
            return clientEntityMapper.map(clientDao.readClientByPassport(passport));
        } catch (SQLException exception) {
            throw new FailedReadException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }

    private void updateRooms() {
        try {
            List<OrderEntity> orders = orderDao.readOrders();
            for (OrderEntity order : orders) {
                if (order.getTime() <= System.currentTimeMillis() / 1000) {
                    orderDao.delete(order);
                }
            }
        } catch (SQLException exception) {
            Log.getInstance().error(exception.getMessage(), this.getClass());
        }
    }
}