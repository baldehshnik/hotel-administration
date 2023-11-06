package com.vd.university.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.vd.university.data.source.HotelDatabase;
import com.vd.university.util.annotation.ORMUsage;

@DatabaseTable(tableName = HotelDatabase.ROOM_ENTITY)
public class RoomEntity {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "room_number", unique = true, canBeNull = false)
    private int number;

    @DatabaseField(columnName = "beds_count", canBeNull = false)
    private int bedsCount;

    @DatabaseField(columnName = "type", canBeNull = false)
    private String type;

    @ORMUsage
    public RoomEntity() {}

    public RoomEntity(int number, int bedsCount, String type) {
        this.number = number;
        this.bedsCount = bedsCount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public String getType() {
        return type;
    }
}