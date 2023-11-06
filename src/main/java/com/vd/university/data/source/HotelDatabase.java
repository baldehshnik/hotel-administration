package com.vd.university.data.source;

import com.vd.university.data.dao.client.ClientDao;
import com.vd.university.data.dao.order.OrderDao;
import com.vd.university.data.dao.room.RoomDao;
import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;
import com.vd.university.di.annotation.SingletonScope;

@SingletonScope
public class HotelDatabase {

    public static final String DATABASE_PATH = "jdbc:sqlite:hotel.db";

    public static final String CLIENT_TABLE = "client";
    public static final String ORDER_TABLE = "order";
    public static final String ROOM_ENTITY = "room";

    private static HotelDatabase database = null;

    private final ConnectionDataSource connectionDataSource;

    private ClientDao clientDao;
    private OrderDao orderDao;
    private RoomDao roomDao;

    private HotelDatabase() {
        this.connectionDataSource = ConnectionDataSource.getInstance();
        initTables();
        createDao();
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public static HotelDatabase getInstance() {
        if (database == null) database = new HotelDatabase();
        return database;
    }

    private void createDao() {
        clientDao = new ClientDao(connectionDataSource.createDao(ClientEntity.class));
        orderDao = new OrderDao(connectionDataSource.createDao(OrderEntity.class));
        roomDao = new RoomDao(connectionDataSource.createDao(RoomEntity.class));
    }

    private void initTables() {
        connectionDataSource.createTableIfNotExists(ClientEntity.class);
        connectionDataSource.createTableIfNotExists(RoomEntity.class);
        connectionDataSource.createTableIfNotExists(OrderEntity.class);
    }
}