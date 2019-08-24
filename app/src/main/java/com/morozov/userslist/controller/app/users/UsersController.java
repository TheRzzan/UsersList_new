package com.morozov.userslist.controller.app.users;

import com.morozov.userslist.R;
import com.morozov.userslist.controller.Controller;
import com.morozov.userslist.controller.interaction.ViewClick;
import com.morozov.userslist.controller.interaction.model.ErrorModel;
import com.morozov.userslist.controller.interaction.model.res.StringResData;
import com.morozov.userslist.models.UserModel;
import com.morozov.userslist.repository.user.UsersLoader;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class UsersController extends Controller<UsersViewModel> {
    @Inject
    UsersLoader usersLoader;

    @Inject
    @Named("mainThread")
    Scheduler mainThread;

    @Inject
    @Named("background")
    Scheduler background;

    private Disposable disposable;

    public UsersController(UsersViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void onStart() {
        initialUsers();
    }

    private void initialUsers() {
        viewModel().showProgress().setValue(true);

        Single.fromCallable(() -> {
            List<UserModel> users = usersLoader.loadDataFromSQLite();

            if (users.size() <= 0)
                return usersLoader.loadDataFromNet();
            else
                return users;
        })
                .observeOn(mainThread)
                .subscribeOn(background)
                .subscribe(new SingleObserver<List<UserModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<UserModel> userModels) {
                        loadSuccess(userModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        loadFailure();
                    }
                });
    }

    private void loadSuccess(List<UserModel> users) {
        viewModel().showProgress().setValue(false);

        viewModel().users().setValue(users);
    }

    private void loadFailure() {
        viewModel().showProgress().setValue(false);

        viewModel().error().setValue(new ErrorModel(
                new StringResData(R.string.app_error_title_generic),
                new StringResData(R.string.app_error_body_generic)
        ));
    }

    @Override
    protected void onStop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    protected Consumer<ViewClick> click() {
        return viewClick -> {
            switch (viewClick.id()) {
                case R.id.inline_error_retry:
                    initialUsers();
                    break;
            }
        };
    }
}
