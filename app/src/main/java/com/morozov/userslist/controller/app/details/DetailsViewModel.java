package com.morozov.userslist.controller.app.details;

import android.arch.lifecycle.MutableLiveData;

import com.morozov.userslist.controller.UiViewModel;
import com.morozov.userslist.models.UserModel;

import java.util.List;

public class DetailsViewModel extends UiViewModel {
    private MutableLiveData<UserModel> user;
    private MutableLiveData<List<UserModel>> friends;
    private MutableLiveData<Integer> selectedFriend;

    public DetailsViewModel() {
        user = new MutableLiveData<>();
        friends = new MutableLiveData<>();
        selectedFriend = new MutableLiveData<>();
    }

    MutableLiveData<UserModel> user() {
        return user;
    }

    MutableLiveData<List<UserModel>> friends() {
        return friends;
    }

    MutableLiveData<Integer> selectedFriend() {
        return selectedFriend;
    }
}
