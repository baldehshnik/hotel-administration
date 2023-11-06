package com.vd.university.presentation.mapper;

import com.vd.university.data.entity.RoomEntity;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.util.mapper.Mapper;

public class RoomModelMapper implements Mapper<RoomModel, RoomEntity> {

    @Override
    public RoomEntity map(RoomModel value) {
        return new RoomEntity(
                value.number(),
                value.bedsCount(),
                value.type().name()
        );
    }
}