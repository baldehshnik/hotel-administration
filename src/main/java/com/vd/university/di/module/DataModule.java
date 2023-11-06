package com.vd.university.di.module;

import com.vd.university.data.dao.client.ClientDao;
import com.vd.university.data.dao.order.OrderDao;
import com.vd.university.data.dao.room.RoomDao;
import com.vd.university.data.mapper.ClientEntityMapper;
import com.vd.university.data.mapper.OrderEntityMapper;
import com.vd.university.data.mapper.RoomEntityMapper;
import com.vd.university.data.repository.AdministratorDataRepository;
import com.vd.university.data.repository.ClientDataRepository;
import com.vd.university.data.repository.DatabaseInitDataRepository;
import com.vd.university.data.source.HotelDatabase;
import com.vd.university.data.source.RoomsFileDataSource;
import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.presentation.repository.IDatabaseInitRepository;

@SingletonScope
public class DataModule {

    private HotelDatabase hotelDatabase = null;

    private RoomsFileDataSource roomsFileDataSource = null;

    private ClientDataRepository clientRepository = null;
    private IAdministratorRepository administratorRepository = null;
    private IDatabaseInitRepository databaseInitRepository = null;

    public HotelDatabase getHotelDatabase() {
        if (hotelDatabase == null) hotelDatabase = createHotelDatabase();
        return hotelDatabase;
    }

    public ClientDao getClientDao() {
        return getHotelDatabase().getClientDao();
    }

    public OrderDao getOrderDao() {
        return getHotelDatabase().getOrderDao();
    }

    public RoomDao getRoomDao() {
        return getHotelDatabase().getRoomDao();
    }

    public RoomEntityMapper getRoomEntityMapper() {
        return new RoomEntityMapper();
    }

    public ClientEntityMapper getClientEntityMapper() {
        return new ClientEntityMapper();
    }

    public OrderEntityMapper getOrderEntityMapper() {
        return new OrderEntityMapper(getClientEntityMapper(), getRoomEntityMapper());
    }

    public ClientDataRepository getClientRepository() {
        if (clientRepository == null) clientRepository = createClientDataRepository();
        return clientRepository;
    }

    public IAdministratorRepository getIAdministratorRepository() {
        if (administratorRepository == null) administratorRepository = createAdministratorRepository();
        return administratorRepository;
    }

    public IDatabaseInitRepository getDatabaseInitRepository() {
        if (databaseInitRepository == null) databaseInitRepository = createDatabaseInitRepository();
        return databaseInitRepository;
    }

    public RoomsFileDataSource getRoomsFileDataSource() {
        if (roomsFileDataSource == null) roomsFileDataSource = createRoomsFileDataSource();
        return roomsFileDataSource;
    }

    private RoomsFileDataSource createRoomsFileDataSource() {
        return new RoomsFileDataSource();
    }

    private IDatabaseInitRepository createDatabaseInitRepository() {
        return new DatabaseInitDataRepository(getRoomDao(), getRoomsFileDataSource());
    }

    private IAdministratorRepository createAdministratorRepository() {
        return new AdministratorDataRepository(getClientDao(), getOrderDao(), getRoomDao(), getClientEntityMapper(), getOrderEntityMapper());
    }

    private ClientDataRepository createClientDataRepository() {
        return new ClientDataRepository(getClientDao(), getOrderDao(), getRoomDao(), getRoomEntityMapper(), getClientEntityMapper());
    }

    private HotelDatabase createHotelDatabase() {
        return HotelDatabase.getInstance();
    }
}