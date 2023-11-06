package com.vd.university.data.mapper;

import com.vd.university.data.entity.RoomEntity;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.presentation.entity.RoomType;
import com.vd.university.util.mapper.Mapper;

public class RoomEntityMapper implements Mapper<RoomEntity, RoomModel> {

    @Override
    public RoomModel map(RoomEntity value) {
        return new RoomModel(
                value.getId(),
                value.getNumber(),
                value.getBedsCount(),
                RoomType.valueOf(value.getType())
        );
    }
}