package com.vd.university.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.vd.university.data.source.HotelDatabase;
import com.vd.university.util.annotation.ORMUsage;

@DatabaseTable(tableName = HotelDatabase.CLIENT_TABLE)
public class ClientEntity {

    @DatabaseField(columnName = "id", generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "firstname")
    private String firstname;

    @DatabaseField(columnName = "lastname")
    private String lastname;

    @DatabaseField(columnName = "patronymic")
    private String patronymic;

    @DatabaseField(columnName = "passport", unique = true)
    private String passport;

    @ORMUsage
    public ClientEntity() {}

    public ClientEntity(String firstname, String lastname, String patronymic, String passport) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.passport = passport;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPassport() {
        return passport;
    }
}