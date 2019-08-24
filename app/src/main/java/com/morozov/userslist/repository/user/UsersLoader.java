package com.morozov.userslist.repository.user;

import com.morozov.userslist.models.UserModel;

import java.util.List;

public interface UsersLoader {
    List<UserModel> loadDataFromSQLite();
    List<UserModel> loadDataFromNet();
}
