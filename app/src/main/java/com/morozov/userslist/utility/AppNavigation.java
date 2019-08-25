package com.morozov.userslist.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.morozov.userslist.controller.app.details.DetailsActivity;
import com.morozov.userslist.controller.app.users.UsersActivity;

public class AppNavigation {

    /* For navigation */

    private static AppNavigation instance;
    private Context context;

    private Integer currentUser;

    public AppNavigation(Context context) {
        this.context = context;
    }

    public static AppNavigation getInstance(Context context) {
        if (instance == null)
            instance = new AppNavigation(context);

        return instance;
    }

    public Integer getCurrentUser() {
        if (currentUser == null)
            currentUser = -1;

        return currentUser;
    }

    public void setCurrentUser(Integer currentUser) {
        this.currentUser = currentUser;
    }




    /* For invoke new Activity */

    public static void invokeNewActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public static void invokeUserDetailsActivity(Activity activity, Integer userId, boolean shouldFinish) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(AppConstants.EXTRA_KEY_USER_ID, userId);

        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        if (shouldFinish) {
            activity.finish();
        }
    }
}
