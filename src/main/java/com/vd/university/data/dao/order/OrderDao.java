package com.vd.university.data.dao.order;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;

import java.sql.SQLException;
import java.util.List;

public class OrderDao implements IOrderDao {

    private final Dao<OrderEntity, Integer> dao;

    public OrderDao(Dao<OrderEntity, Integer> dao) {
        this.dao = dao;
    }

    @Override
    public List<OrderEntity> readOrders() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public void delete(OrderEntity order) throws SQLException {
        dao.delete(order);
    }

    @Override
    public QueryBuilder<OrderEntity, ?> readQueryToReadRoomsIds() throws SQLException {
        return dao.queryBuilder().selectColumns("room_id");
    }

    @Override
    public void createOrder(OrderEntity order) throws SQLException {
        dao.create(order);
    }

    @Override
    public OrderEntity readOrderByRoomId(RoomEntity room) throws SQLException {
        QueryBuilder<OrderEntity, ?> builder = dao.queryBuilder().selectColumns("room_id");
        builder.where().eq("room_id", room);

        return builder.queryForFirst();
    }

    @Override
    public OrderEntity readOrderByClientId(ClientEntity client) throws SQLException {
        QueryBuilder<OrderEntity, ?> builder = dao.queryBuilder().selectColumns("client_id");
        builder.where().eq("client_id", client);

        return builder.queryForFirst();
    }

    @Override
    public int cancelOrderByRoomNumber(int roomNumber, QueryBuilder<RoomEntity, Integer> roomEntityQueryBuilder) throws SQLException {
        roomEntityQueryBuilder.where().eq("room_number", roomNumber);
        RoomEntity room = roomEntityQueryBuilder.queryForFirst();

        if (room != null) {
            DeleteBuilder<OrderEntity, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq("room_id", room);
            return deleteBuilder.delete();
        } else {
            return 0;
        }
    }
}