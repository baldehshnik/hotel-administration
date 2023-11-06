package com.vd.university.presentation.screen.admin.orders;

import com.vd.university.presentation.entity.OrderModel;

import java.util.List;

public interface CurrentOrdersContract {

    interface View {

        void handleCurrentOrdersReading(List<OrderModel> orders);

        void onError(String message);
    }

    interface Presenter {

        void readCurrentOrders();

        void onDestroy();
    }
}