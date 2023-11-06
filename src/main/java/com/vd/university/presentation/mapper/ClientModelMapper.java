package com.vd.university.presentation.mapper;

import com.vd.university.data.entity.ClientEntity;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.util.mapper.Mapper;

public class ClientModelMapper implements Mapper<ClientModel, ClientEntity> {

    @Override
    public ClientEntity map(ClientModel value) {
        return new ClientEntity(
                value.firstname(),
                value.lastname(),
                value.patronymic(),
                value.passport()
        );
    }
}