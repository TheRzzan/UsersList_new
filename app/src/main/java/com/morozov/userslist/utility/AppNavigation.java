package com.morozov.userslist.utility;

import android.app.Activity;
import android.content.Intent;

import com.morozov.userslist.controller.app.users.UsersActivity;

public class AppNavigation {
    public static void invokeNewActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public static void invokeUserDetailsActivity(Activity activity, Integer userId, boolean shouldFinish) {
        Intent intent = new Intent(activity, UsersActivity.class);
        intent.putExtra(AppConstants.EXTRA_KEY_USER_ID, userId);

        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        if (shouldFinish) {
            activity.finish();
        }
    }
}
