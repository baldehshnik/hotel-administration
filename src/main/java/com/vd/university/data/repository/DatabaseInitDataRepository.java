package com.vd.university.data.repository;

import com.vd.university.data.dao.room.IRoomDao;
import com.vd.university.data.source.RoomsFileDataSource;
import com.vd.university.di.annotation.SingletonScope;
import com.vd.university.presentation.repository.IDatabaseInitRepository;
import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.InitFileReadingException;
import com.vd.university.util.exception.UnknownException;

import java.io.IOException;
import java.sql.SQLException;

@SingletonScope
public class DatabaseInitDataRepository implements IDatabaseInitRepository {

    private final IRoomDao roomDao;
    private final RoomsFileDataSource roomsFileDataSource;

    public DatabaseInitDataRepository(IRoomDao roomDao, RoomsFileDataSource roomsFileDataSource) {
        this.roomDao = roomDao;
        this.roomsFileDataSource = roomsFileDataSource;
    }

    @Override
    public void insertRooms() throws FailedInsertionException, InitFileReadingException, UnknownException {
        try {
            roomDao.insertRooms(roomsFileDataSource.readRooms());
        } catch (SQLException exception) {
            throw new FailedInsertionException(exception.getMessage(), this.getClass());
        } catch (IOException exception) {
            throw new InitFileReadingException(exception.getMessage(), this.getClass());
        } catch (Exception exception) {
            throw new UnknownException(exception.getMessage(), this.getClass());
        }
    }
}