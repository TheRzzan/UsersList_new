package com.morozov.userslist.controller.app.details;

import com.morozov.userslist.controller.ControllerComponent;
import com.morozov.userslist.controller.app.users.UsersController;
import com.morozov.userslist.repository.UsersModule;
import com.morozov.userslist.system.RxModule;

import dagger.Component;

@Component(modules = {
        RxModule.class,
        UsersModule.class
})
public interface DetailsComponent extends ControllerComponent {
    String DETAILS_COMPONENT = "DETAILS_COMPONENT";

    void inject(DetailsController controller);
}
