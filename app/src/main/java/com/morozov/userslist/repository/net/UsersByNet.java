package com.morozov.userslist.repository.net;

import com.morozov.userslist.models.UserModel;

import java.util.List;

public interface UsersByNet {
    List<UserModel> loadUsersFromNet();
}
