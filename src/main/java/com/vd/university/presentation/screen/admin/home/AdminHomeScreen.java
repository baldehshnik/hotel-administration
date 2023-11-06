package com.vd.university.presentation.screen.admin.home;

import com.vd.university.di.ApplicationComponent;
import com.vd.university.presentation.screen.Screen;
import com.vd.university.presentation.screen.admin.cancel_order.CancelOrderScreen;
import com.vd.university.presentation.screen.admin.clients.ViewClientsScreen;
import com.vd.university.presentation.screen.admin.orders.CurrentOrdersScreen;
import com.vd.university.presentation.screen.admin.update.UpdateScreen;
import com.vd.university.util.log.Log;

public class AdminHomeScreen extends Screen implements AdminHomeContract.View {

    private final ApplicationComponent applicationComponent;

    private final AdminHomeContract.Presenter presenter;

    private final Log log = Log.getInstance();

    private AdminHomeScreen(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        this.presenter = applicationComponent.adminHomePresenter(this);
    }

    @Override
    public void onCreate() {
        System.out.println("\n\nWelcome!");
    }

    @Override
    public void handleCorrectReportCreation() {
        System.out.println("The report creation was finished successfully!");
    }

    @Override
    public void createReport() {
        presenter.createReport();
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        System.out.println("\n\n<<Program was finished!>>\n\n");
    }

    public void showMenu() {
        System.out.println(
                """
                        
                        Please select the required operation!
                        
                        1. View current orders
                        2. View clients
                        3. Cancel reservation
                        4. Make changes to the database
                        5. Create report
                        6. Finish work
                        
                        """
        );
        System.out.print("Make your choice: ");
    }

    public void showCurrentOrdersScreen() {
        CurrentOrdersScreen currentOrdersScreen = CurrentOrdersScreen.getInstance(applicationComponent);

        log.info("CurrentOrdersScreen was showed");
        currentOrdersScreen.onCreate();

        log.info("CurrentOrdersScreen was hided");
        currentOrdersScreen.onDestroy();
    }

    public void showViewClientsScreen() {
        ViewClientsScreen viewClientsScreen = ViewClientsScreen.getInstance(applicationComponent);

        log.info("ViewClientsScreen was showed");
        viewClientsScreen.onCreate();

        log.info("ViewClientsScreen was hided");
        viewClientsScreen.onDestroy();
    }

    public void showCancelOrderScreen() {
        CancelOrderScreen cancelOrderScreen = CancelOrderScreen.getInstance(applicationComponent);

        log.info("CancelOrderScreen was showed");
        cancelOrderScreen.onCreate();

        log.info("CancelOrderScreen was hided");
        cancelOrderScreen.onDestroy();
    }

    public void showUpdateScreen() {
        UpdateScreen updateScreen = UpdateScreen.getInstance(applicationComponent);

        log.info("UpdateScreen was showed");
        updateScreen.onCreate();

        log.info("UpdateScreen was hided");
        updateScreen.onDestroy();
    }

    public static AdminHomeScreen getInstance(ApplicationComponent applicationComponent) {
        return new AdminHomeScreen(applicationComponent);
    }
}