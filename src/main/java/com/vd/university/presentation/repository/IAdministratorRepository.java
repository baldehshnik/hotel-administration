package com.vd.university.presentation.repository;

import com.vd.university.data.entity.RoomEntity;
import com.vd.university.presentation.entity.ClientModel;
import com.vd.university.presentation.entity.OrderModel;
import com.vd.university.util.exception.*;

import java.util.List;

public interface IAdministratorRepository {

    void createReport() throws FailedReadException, UnknownException, ReportFileUsageException;

    boolean deleteClientByPassportIfNotBookingRoom(String passport) throws FailedDeletionException, ClientCurrentlyInHotelException, UnknownException;

    boolean createRoom(RoomEntity room) throws FailedInsertionException, UnknownException;

    List<ClientModel> readClients() throws FailedReadException, UnknownException;

    List<OrderModel> readCurrentOrders() throws FailedReadException, UnknownException;

    boolean cancelOrderByRoomNumber(int roomNumber) throws FailedDeletionException, UnknownException;

    boolean deleteRoomByRoomNumberIfNotReserved(int roomNumber) throws FailedDeletionException, UnknownException, RoomReservedException;

}