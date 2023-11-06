package com.vd.university.data.mapper;

import com.vd.university.data.entity.OrderEntity;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.util.mapper.Mapper;

public class OrderEntityMapper implements Mapper<OrderEntity, OrderModel> {

    private final ClientEntityMapper clientEntityMapper;
    private final RoomEntityMapper roomEntityMapper;

    public OrderEntityMapper(ClientEntityMapper clientEntityMapper, RoomEntityMapper roomEntityMapper) {
        this.clientEntityMapper = clientEntityMapper;
        this.roomEntityMapper = roomEntityMapper;
    }

    @Override
    public OrderModel map(OrderEntity value) {
        return new OrderModel(
                value.getId(),
                clientEntityMapper.map(value.getClient()),
                value.getTime(),
                roomEntityMapper.map(value.getRoom())
        );
    }
}