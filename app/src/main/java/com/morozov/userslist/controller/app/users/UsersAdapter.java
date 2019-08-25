package com.morozov.userslist.controller.app.users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.morozov.userslist.R;
import com.morozov.userslist.controller.interaction.OnUserClickListener;
import com.morozov.userslist.controller.ui.ListAdapter;
import com.morozov.userslist.models.UserModel;

public class UsersAdapter extends ListAdapter<UserModel, UsersViewHolder> {

    private final LayoutInflater inflater;
    private final OnUserClickListener listener;

    public UsersAdapter(Context context, OnUserClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UsersViewHolder(inflater.inflate(R.layout.item_user, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int i) {
        holder.populate(data().get(i), listener);
    }
}
