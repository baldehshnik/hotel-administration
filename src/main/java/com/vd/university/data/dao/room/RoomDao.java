package com.vd.university.data.dao.room;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;

import java.sql.SQLException;
import java.util.List;

public class RoomDao implements IRoomDao {

    private final Dao<RoomEntity, Integer> dao;

    public RoomDao(Dao<RoomEntity, Integer> dao) {
        this.dao = dao;
    }

    @Override
    public void insertRooms(List<RoomEntity> rooms) throws SQLException {
        for (RoomEntity room : rooms) {
            dao.createOrUpdate(room);
        }
    }

    @Override
    public List<RoomEntity> readEmptyRooms(QueryBuilder<OrderEntity, ?> subQuery) throws SQLException {
        QueryBuilder<RoomEntity, Integer> roomQueryBuilder = dao.queryBuilder();
        roomQueryBuilder.where().notIn("id", subQuery);

        return dao.query(roomQueryBuilder.prepare());
    }

    @Override
    public List<RoomEntity> readAllRooms() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public int delete(int roomNumber) throws SQLException {
        DeleteBuilder<RoomEntity, Integer> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("room_number", roomNumber);

        return deleteBuilder.delete();
    }

    @Override
    public int create(RoomEntity room) throws SQLException {
        return dao.create(room);
    }

    @Override
    public RoomEntity readRoomByNumber(int roomNumber) throws SQLException {
        QueryBuilder<RoomEntity, Integer> builder = dao.queryBuilder();
        builder.where().eq("room_number", roomNumber);
        builder.limit(1L);

        return builder.queryForFirst();
    }

    @Override
    public QueryBuilder<RoomEntity, Integer> getDaoBuilder() {
        return dao.queryBuilder();
    }
}