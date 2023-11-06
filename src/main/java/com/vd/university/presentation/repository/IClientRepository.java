package com.vd.university.presentation.repository;

import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.RoomModel;
import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.UnknownException;

import java.util.List;

public interface IClientRepository {

    List<RoomModel> readEmptyRooms() throws FailedReadException, UnknownException;

    void registerClient(ClientEntity client) throws FailedInsertionException, UnknownException;

    boolean createOrder(OrderEntity order) throws FailedInsertionException, UnknownException;

    ClientModel readClientByPassport(String passport) throws FailedReadException, UnknownException;

}