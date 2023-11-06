package com.vd.university.data.mapper;

import com.vd.university.data.entity.ClientEntity;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.util.mapper.Mapper;

public class ClientEntityMapper implements Mapper<ClientEntity, ClientModel> {

    @Override
    public ClientModel map(ClientEntity value) {
        if (value == null) return null;
        return new ClientModel(value.getId(), value.getFirstname(), value.getLastname(), value.getPatronymic(), value.getPassport());
    }
}