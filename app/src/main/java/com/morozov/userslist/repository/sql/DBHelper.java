package com.morozov.userslist.repository.sql;

import android.content.Context;

import com.morozov.userslist.models.UserModel;

public interface DBHelper {
    String LOG_TAG = "DBHelper";

    Context getContext();

    UserModel getItemAt(int position);

    UserModel getItemById(int userId);

    void removeItemWithId(int id);

    void removeAll();

    int getCount();

    long addUser(UserModel user);
}
