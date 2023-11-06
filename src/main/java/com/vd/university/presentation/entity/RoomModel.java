package com.vd.university.presentation.entity;

public record RoomModel(int id, int number, int bedsCount, RoomType type) {

    public RoomModel(int number, int bedsCount, RoomType type) {
        this(0, number, bedsCount, type);
    }
}