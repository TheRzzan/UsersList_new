package com.morozov.userslist.controller.app.users;

import android.arch.lifecycle.MutableLiveData;

import com.morozov.userslist.controller.UiViewModel;
import com.morozov.userslist.controller.interaction.model.ErrorModel;
import com.morozov.userslist.models.UserModel;

import java.util.List;

public class UsersViewModel extends UiViewModel {
    private MutableLiveData<List<UserModel>> users;
    private MutableLiveData<Integer> selectedUser;
    private MutableLiveData<Boolean> addNew;

    private MutableLiveData<ErrorModel> error;

    public UsersViewModel() {
        users = new MutableLiveData<>();
        selectedUser= new MutableLiveData<>();
        addNew= new MutableLiveData<>();

        error = new MutableLiveData<>();
    }

    MutableLiveData<List<UserModel>> users() {
        return users;
    }

    MutableLiveData<Integer> selectedUser() {
        return  selectedUser;
    }

    MutableLiveData<Boolean> addNew() {
        return addNew;
    }

    MutableLiveData<ErrorModel> error() {
        return error;
    }
}
