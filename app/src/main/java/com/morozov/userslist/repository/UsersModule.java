package com.morozov.userslist.repository;

import com.morozov.userslist.repository.user.UsersLoader;
import com.morozov.userslist.repository.user.UsersLoaderImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersModule {

    @Provides
    UsersLoader usersLoader() {
        return new UsersLoaderImpl();
    }

}
