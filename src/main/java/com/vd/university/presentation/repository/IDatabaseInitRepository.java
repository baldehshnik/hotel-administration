package com.vd.university.presentation.repository;

import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.InitFileReadingException;
import com.vd.university.util.exception.UnknownException;

public interface IDatabaseInitRepository {

    void insertRooms() throws FailedInsertionException, InitFileReadingException, UnknownException;

}