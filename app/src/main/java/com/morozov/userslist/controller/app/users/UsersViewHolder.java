package com.morozov.userslist.controller.app.users;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.morozov.userslist.R;
import com.morozov.userslist.controller.interaction.OnUserClickListener;
import com.morozov.userslist.models.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_item_name)
    TextView name;

    @BindView(R.id.textView_item_email)
    TextView email;

    @BindView(R.id.isActiveImage)
    ImageView isActiveImage;

    @BindView(R.id.isActiveText)
    TextView isActiveText;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void populate(UserModel user, OnUserClickListener listener) {
        name.setText(user.getName());
        email.setText(user.getEmail());
        setIsActive(itemView.getContext(), user.getIsActive());
        itemView.setOnClickListener(v -> {
            if (user.getIsActive())
                listener.onUserClick(user.getId());
        });
    }

    private void setIsActive(Context context, Boolean b) {
        if (b) {
            isActiveText.setText(itemView.getContext().getString(R.string.active));

            Drawable background = isActiveImage.getBackground();
            if (background instanceof GradientDrawable) {
                int drawableId = ContextCompat.getColor(context, R.color.activeUser);
                ((GradientDrawable) background).setColor(drawableId);
            }
        } else {
            isActiveText.setText(itemView.getContext().getString(R.string.not_active));

            Drawable background = isActiveImage.getBackground();
            if (background instanceof GradientDrawable) {
                int drawableId = ContextCompat.getColor(context, R.color.inactiveUser);
                ((GradientDrawable) background).setColor(drawableId);
            }
        }
    }
}
