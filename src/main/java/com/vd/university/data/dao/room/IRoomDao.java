package com.vd.university.data.dao.room;

import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;

import java.sql.SQLException;
import java.util.List;

public interface IRoomDao {

    void insertRooms(List<RoomEntity> rooms) throws SQLException;

    List<RoomEntity> readEmptyRooms(QueryBuilder<OrderEntity, ?> subQuery) throws SQLException;

    List<RoomEntity> readAllRooms() throws SQLException;

    int delete(int roomNumber) throws SQLException;

    int create(RoomEntity room) throws SQLException;

    RoomEntity readRoomByNumber(int roomNumber) throws SQLException;

    QueryBuilder<RoomEntity, Integer> getDaoBuilder();

}