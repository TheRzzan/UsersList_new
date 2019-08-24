package com.morozov.userslist.repository.user;

import com.morozov.userslist.models.UserModel;
import com.morozov.userslist.repository.net.UsersByNet;
import com.morozov.userslist.repository.sql.DBHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public abstract class UsersLoader {
    @Inject
    UsersByNet usersByNet;

    @Inject
    DBHelper dbHelper;

    public abstract List<UserModel> loadDataFromSQLite();

    public abstract List<UserModel> loadDataFromNet();
}
