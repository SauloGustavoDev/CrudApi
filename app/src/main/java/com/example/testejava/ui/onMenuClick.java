package com.example.testejava.ui;

import com.example.testejava.model.Car;

public interface onMenuClick {
    void onEdit(
            Car car
    );

    void onSaveinCache(
            Car car
    );

    void onDelete(
            String id
    );
}
