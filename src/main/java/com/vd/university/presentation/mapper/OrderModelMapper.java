package com.vd.university.presentation.mapper;

import com.vd.university.data.entity.OrderEntity;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.util.mapper.Mapper;

public class OrderModelMapper implements Mapper<OrderModel, OrderEntity> {

    private final ClientModelMapper clientModelMapper;
    private final RoomModelMapper roomModelMapper;

    public OrderModelMapper(ClientModelMapper clientModelMapper, RoomModelMapper roomModelMapper) {
        this.clientModelMapper = clientModelMapper;
        this.roomModelMapper = roomModelMapper;
    }

    @Override
    public OrderEntity map(OrderModel value) {
        return new OrderEntity(
                clientModelMapper.map(value.client()),
                value.time(),
                roomModelMapper.map(value.room())
        );
    }
}