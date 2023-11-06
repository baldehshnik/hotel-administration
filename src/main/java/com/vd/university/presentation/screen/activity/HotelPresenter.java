package com.vd.university.presentation.screen.activity;

import com.vd.university.presentation.repository.IDatabaseInitRepository;
import com.vd.university.util.exception.FailedInsertionException;
import com.vd.university.util.exception.InitFileReadingException;
import com.vd.university.util.exception.UnknownException;

public class HotelPresenter implements HotelContract.Presenter {

    private final IDatabaseInitRepository repository;

    private HotelContract.View view;

    public HotelPresenter(IDatabaseInitRepository repository, HotelContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void initDatabase() {
        try {
            repository.insertRooms();
            view.handleCorrectDatabaseInitialize();
        } catch (FailedInsertionException e) {
            view.onError("An error occurred while inserting data");
        } catch (InitFileReadingException e) {
            view.onError("The initialization file is corrupted!");
        } catch (UnknownException e) {
            view.onError("An unknown error occurred!");
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}