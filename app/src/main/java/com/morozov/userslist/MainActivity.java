package com.morozov.userslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.morozov.userslist.controller.app.users.UsersActivity;
import com.morozov.userslist.utility.AppNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppNavigation.invokeNewActivity(MainActivity.this, UsersActivity.class, true);
    }
}
