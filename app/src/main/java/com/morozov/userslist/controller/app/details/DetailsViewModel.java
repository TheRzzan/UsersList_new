package com.morozov.userslist.controller.app.details;

import android.arch.lifecycle.MutableLiveData;

import com.morozov.userslist.controller.UiViewModel;
import com.morozov.userslist.models.UserModel;

import java.util.List;

public class DetailsViewModel extends UiViewModel {
    private MutableLiveData<UserModel> user;
    private MutableLiveData<List<UserModel>> friends;
    private MutableLiveData<Integer> selectedFriend;

    private MutableLiveData<Boolean> gotoEmail;
    private MutableLiveData<Boolean> gotoPhone;
    private MutableLiveData<Boolean> gotoLocation;

    public DetailsViewModel() {
        user = new MutableLiveData<>();
        friends = new MutableLiveData<>();
        selectedFriend = new MutableLiveData<>();

        gotoEmail = new MutableLiveData<>();
        gotoPhone = new MutableLiveData<>();
        gotoLocation = new MutableLiveData<>();
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

    MutableLiveData<Boolean> gotoEmail() {
        return gotoEmail;
    }

    MutableLiveData<Boolean> gotoPhone() {
        return gotoPhone;
    }

    MutableLiveData<Boolean> gotoLocation() {
        return gotoLocation;
    }

}
