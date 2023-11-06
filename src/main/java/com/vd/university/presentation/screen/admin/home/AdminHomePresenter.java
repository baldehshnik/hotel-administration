package com.vd.university.presentation.screen.admin.home;

import com.vd.university.presentation.repository.IAdministratorRepository;
import com.vd.university.util.exception.FailedReadException;
import com.vd.university.util.exception.ReportFileUsageException;
import com.vd.university.util.exception.UnknownException;

public class AdminHomePresenter implements AdminHomeContract.Presenter {

    private final IAdministratorRepository repository;

    private AdminHomeContract.View screen;

    public AdminHomePresenter(IAdministratorRepository repository, AdminHomeContract.View screen) {
        this.repository = repository;
        this.screen = screen;
    }

    @Override
    public void createReport() {
        try {
            repository.createReport();
            screen.handleCorrectReportCreation();
        } catch (FailedReadException e) {
            screen.onError("Failed to receive data, try again later!");
        } catch (UnknownException e) {
            screen.onError("An unknown error occurred!");
        } catch (ReportFileUsageException e) {
            screen.onError("The file used to create the report could not be used!");
        }
    }

    @Override
    public void onDestroy() {
        screen = null;
    }
}