package com.vd.university.data.dao.client;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vd.university.data.entity.ClientEntity;

import java.sql.SQLException;
import java.util.List;

public class ClientDao implements IClientDao {

    private final Dao<ClientEntity, Integer> dao;

    public ClientDao(Dao<ClientEntity, Integer> dao) {
        this.dao = dao;
    }

    @Override
    public ClientEntity readClientById(ClientEntity client) throws SQLException {
        return dao.queryForSameId(client);
    }

    @Override
    public ClientEntity readClientByPassport(String passport) throws SQLException {
        QueryBuilder<ClientEntity, Integer> builder = dao.queryBuilder();
        builder.where().eq("passport", passport);
        builder.limit(1L);

        return builder.queryForFirst();
    }

    @Override
    public void registerClient(ClientEntity client) throws SQLException {
        dao.create(client);
    }

    @Override
    public List<ClientEntity> readClients() throws SQLException {
        return dao.queryForAll();
    }

    @Override
    public int delete(String passport) throws SQLException {
        DeleteBuilder<ClientEntity, Integer> builder = dao.deleteBuilder();
        builder.where().eq("passport", passport);

        return builder.delete();
    }
}