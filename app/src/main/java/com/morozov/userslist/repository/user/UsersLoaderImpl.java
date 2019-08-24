package com.morozov.userslist.repository.user;

import com.morozov.userslist.models.UserModel;

import java.util.List;

public class UsersLoaderImpl implements UsersLoader {
    @Override
    public List<UserModel> loadDataFromSQLite() {
        return null;
    }

    @Override
    public List<UserModel> loadDataFromNet() {
        return null;
    }
}
