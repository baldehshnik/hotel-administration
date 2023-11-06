package com.vd.university.data.dao.order;

import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;

import java.sql.SQLException;
import java.util.List;

public interface IOrderDao {

    List<OrderEntity> readOrders() throws SQLException;

    void delete(OrderEntity order) throws SQLException;

    QueryBuilder<OrderEntity, ?> readQueryToReadRoomsIds() throws SQLException;

    OrderEntity readOrderByRoomId(RoomEntity room) throws SQLException;

    OrderEntity readOrderByClientId(ClientEntity client) throws SQLException;

    void createOrder(OrderEntity order) throws SQLException;

    int cancelOrderByRoomNumber(int roomNumber, QueryBuilder<RoomEntity, Integer> roomEntityQueryBuilder) throws SQLException;

}