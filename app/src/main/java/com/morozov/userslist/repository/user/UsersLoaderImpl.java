package com.morozov.userslist.repository.user;

import com.morozov.userslist.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UsersLoaderImpl extends UsersLoader {
    @Override
    public List<UserModel> loadDataFromSQLite()  {
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
