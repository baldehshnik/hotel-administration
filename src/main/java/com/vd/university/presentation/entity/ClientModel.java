package com.vd.university.presentation.entity;

public record ClientModel(int id, String firstname, String lastname, String patronymic, String passport) {

    public ClientModel(String firstname, String lastname, String patronymic, String passport) {
        this(0, firstname, lastname, patronymic, passport);
    }
}