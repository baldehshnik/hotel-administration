package com.vd.university.data.dao.client;

import com.vd.university.data.entity.ClientEntity;

import java.sql.SQLException;
import java.util.List;

public interface IClientDao {

    ClientEntity readClientById(ClientEntity client) throws SQLException;

    ClientEntity readClientByPassport(String passport) throws SQLException;

    void registerClient(ClientEntity client) throws SQLException;

    List<ClientEntity> readClients() throws SQLException;

    int delete(String passport) throws SQLException;

}