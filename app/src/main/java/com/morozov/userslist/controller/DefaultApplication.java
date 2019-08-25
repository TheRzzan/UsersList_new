package com.morozov.userslist.controller;

import android.app.Application;

import com.morozov.userslist.controller.app.details.DaggerDetailsComponent;
import com.morozov.userslist.controller.app.details.DetailsComponent;
import com.morozov.userslist.controller.app.users.DaggerUserComponent;
import com.morozov.userslist.controller.app.users.UserComponent;
import com.morozov.userslist.repository.LoaderModule;
import com.morozov.userslist.repository.UsersModule;
import com.morozov.userslist.system.RxModule;

import static com.morozov.userslist.controller.app.details.DetailsComponent.DETAILS_COMPONENT;
import static com.morozov.userslist.controller.app.users.UserComponent.USER_COMPONENT;

public class DefaultApplication extends Application {

    private UserComponent userComponent;
    private DetailsComponent detailsComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        userComponent = DaggerUserComponent
                .builder()
                .rxModule(new RxModule())
                .loaderModule(new LoaderModule())
                .usersModule(new UsersModule(getApplicationContext()))
                .build();

        detailsComponent = DaggerDetailsComponent
                .builder()
                .rxModule(new RxModule())
                .usersModule(new UsersModule(getApplicationContext()))
                .build();
    }

    @Override
    public Object getSystemService(String name) {
        switch (name) {
            case USER_COMPONENT:
                return userComponent;
            case DETAILS_COMPONENT:
                return detailsComponent;
            default:
                return super.getSystemService(name);
        }
    }
}
