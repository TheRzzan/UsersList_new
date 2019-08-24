package com.morozov.userslist.controller.app.users;

import com.morozov.userslist.controller.ControllerComponent;
import com.morozov.userslist.repository.LoaderModule;
import com.morozov.userslist.repository.UsersModule;
import com.morozov.userslist.repository.user.UsersLoader;
import com.morozov.userslist.system.RxModule;

import dagger.Component;

@Component(modules = {
        RxModule.class,
        LoaderModule.class,
        UsersModule.class
})
public interface UserComponent extends ControllerComponent {
    String USER_COMPONENT = "USER_COMPONENT";

    void inject(UsersController controller);

    void inject(UsersLoader usersLoader);
}
