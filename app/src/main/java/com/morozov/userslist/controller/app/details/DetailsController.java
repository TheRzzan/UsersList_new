package com.morozov.userslist.controller.app.details;

import android.content.Context;

import com.morozov.userslist.controller.Controller;
import com.morozov.userslist.controller.interaction.OnUserClickListener;
import com.morozov.userslist.controller.interaction.ViewClick;
import com.morozov.userslist.models.FriendModel;
import com.morozov.userslist.models.UserModel;
import com.morozov.userslist.repository.sql.DBHelper;
import com.morozov.userslist.utility.AppNavigation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DetailsController extends Controller<DetailsViewModel> implements OnUserClickListener {

    @Inject
    DBHelper dbHelper;

    @Inject
    @Named("mainThread")
    Scheduler mainThread;

    @Inject
    @Named("background")
    Scheduler background;

    private Disposable disposableUser;

    private Disposable disposableFriends;

    private Context context;

    public DetailsController(DetailsViewModel viewModel) {
        super(viewModel);
    }

    void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onStart() {
        initialUser();
    }

    private void initialUser() {
        viewModel().showProgress().setValue(true);

        Single.fromCallable(() -> dbHelper.getItemById(AppNavigation.getInstance(context).getCurrentUser()))
                .observeOn(mainThread)
                .subscribeOn(background)
                .subscribe(new SingleObserver<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableUser = d;
                    }

                    @Override
                    public void onSuccess(UserModel userModel) {
                        initialFriends(userModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadFailure();
                    }
                });
    }

    private void initialFriends(UserModel user) {
        Single.fromCallable(() -> {
            List<UserModel> friends = new ArrayList<>();
            for (FriendModel friendModel : user.getFriendModels()) {
                friends.add(dbHelper.getItemById(friendModel.getId()));
            }
            return friends;
        })
                .observeOn(mainThread)
                .subscribeOn(background)
                .subscribe(new SingleObserver<List<UserModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableFriends = d;
                    }

                    @Override
                    public void onSuccess(List<UserModel> userModels) {
                        loadSuccess(user, userModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadFailure();
                    }
                });
    }

    private void loadSuccess(UserModel user, List<UserModel> friends) {
        viewModel().showProgress().setValue(false);

        viewModel().user().setValue(user);
        viewModel().friends().setValue(friends);
    }

    private void loadFailure() {
        viewModel().showProgress().setValue(false);
    }

    @Override
    protected void onStop() {
        if (disposableUser != null) {
            disposableUser.dispose();
        }
        if (disposableFriends != null) {
            disposableFriends.dispose();
        }
    }

    @Override
    protected Consumer<ViewClick> click() {
        return viewClick -> {

        };
    }

    @Override
    public void onUserClick(Integer personalId) {
        viewModel().selectedFriend().setValue(personalId);
    }
}
