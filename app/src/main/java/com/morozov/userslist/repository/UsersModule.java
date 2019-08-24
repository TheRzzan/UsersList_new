package com.morozov.userslist.repository;

import android.content.Context;

import com.morozov.userslist.repository.net.UsersByNet;
import com.morozov.userslist.repository.net.UsersByNetImpl;
import com.morozov.userslist.repository.sql.DBHelper;
import com.morozov.userslist.repository.sql.DBHelperImpl;
import com.morozov.userslist.repository.user.UsersLoader;
import com.morozov.userslist.repository.user.UsersLoaderImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {

    private final Context context;

    public UsersModule(Context context) {
        this.context = context;
    }

    @Provides
    DBHelper dbHelper() {
        return new DBHelperImpl(context);
    }

    @Provides
    UsersByNet usersByNet() {
        return new UsersByNetImpl();
    }
}
