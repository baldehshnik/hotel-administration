package com.vd.university.presentation.entity;

public record OrderModel(int id, ClientModel client, long time, RoomModel room) {

    public OrderModel(ClientModel client, long time, RoomModel room) {
        this(0, client, time, room);
    }
}