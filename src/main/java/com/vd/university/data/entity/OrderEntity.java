package com.vd.university.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.vd.university.data.source.HotelDatabase;
import com.vd.university.util.annotation.ORMUsage;

@DatabaseTable(tableName = HotelDatabase.ORDER_TABLE)
public class OrderEntity {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "client_id", foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private ClientEntity client;

    @DatabaseField(columnName = "room_id", foreign = true, foreignAutoRefresh = true, canBeNull = false, unique = true)
    private RoomEntity room;

    @DatabaseField(columnName = "order_time")
    private long time;

    @ORMUsage
    public OrderEntity() {}

    public OrderEntity(ClientEntity client, long time, RoomEntity room) {
        this.client = client;
        this.time = time;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public long getTime() {
        return time;
    }

    public RoomEntity getRoom() {
        return room;
    }
}