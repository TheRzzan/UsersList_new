package com.morozov.userslist.controller.app.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.morozov.userslist.R;
import com.morozov.userslist.controller.ControllerActivity;
import com.morozov.userslist.controller.ui.FrameErrorView;
import com.morozov.userslist.utility.AppNavigation;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.morozov.userslist.controller.app.users.UserComponent.USER_COMPONENT;

public class UsersActivity extends ControllerActivity<UsersViewModel, UsersController, UserComponent> {

    @BindView(R.id.users_list_container)
    View userListContainer;

    @BindView(R.id.user_activity_frame_error)
    FrameErrorView errorFrame;

    @BindView(R.id.recyclerView_users_list)
    RecyclerView recyclerView;

    @BindView(R.id.fab_refresh)
    FloatingActionButton fabRefresh;

    @BindView(R.id.user_activity_progress_bar)
    ProgressBar progressBar;

    UsersAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        ButterKnife.bind(this);

        adapter = new UsersAdapter(getApplicationContext(), controller);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        observeClicks(errorFrame.click(), fabRefresh);
    }

    @Override
    protected void observe(UsersViewModel viewModel) {
        super.observe(viewModel);

        viewModel.error().observe(this, errorModel -> {
            errorFrame.setVisibility(View.VISIBLE);
            userListContainer.setVisibility(View.GONE);
            errorFrame.title().setText(getResources().getString(errorModel.title().id()));
            errorFrame.body().setText(getResources().getString(errorModel.body().id()));
        });

        viewModel.showProgress().observe(this, showProgress -> {
            errorFrame.setVisibility(View.GONE);
            userListContainer.setVisibility(View.GONE);
            progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        });

        viewModel.users().observe(this, users -> {
            adapter.setData(users);
            adapter.notifyDataSetChanged();
            userListContainer.setVisibility(View.VISIBLE);
        });

        viewModel.selectedUser().observe(this, integer ->
                AppNavigation.invokeUserDetailsActivity(UsersActivity.this, integer, false)
        );
    }

    @Override
    protected UsersController createController(UsersViewModel viewModel) {
        UsersController usersController = new UsersController(viewModel);
        injector(USER_COMPONENT).inject(usersController);
        injector(USER_COMPONENT).inject(usersController.usersLoader);
        return usersController;
    }

    @Override
    protected Class<UsersViewModel> viewModel() {
        return UsersViewModel.class;
    }
}
