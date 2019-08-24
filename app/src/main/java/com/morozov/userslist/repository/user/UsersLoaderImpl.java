package com.morozov.userslist.repository.user;

import com.morozov.userslist.models.UserModel;
import com.morozov.userslist.repository.net.UsersByNet;
import com.morozov.userslist.repository.sql.DBHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UsersLoaderImpl implements UsersLoader {
    @Inject
    UsersByNet usersByNet;

    @Inject
    DBHelper dbHelper;

    @Override
    public List<UserModel> loadDataFromSQLite() {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < dbHelper.getCount(); i++) {
            users.add(dbHelper.getItemAt(i));
        }
        return users;
    }

    @Override
    public List<UserModel> loadDataFromNet() {
        return usersByNet.loadUsersFromNet();
    }
}
