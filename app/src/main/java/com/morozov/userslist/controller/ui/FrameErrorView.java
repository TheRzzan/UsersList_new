package com.morozov.userslist.controller.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.morozov.userslist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameErrorView extends FrameLayout {

    @BindView(R.id.inline_error_title)
    TextView titleTextView;

    @BindView(R.id.inline_error_body)
    TextView bodyTextView;

    @BindView(R.id.inline_error_retry)
    Button retryButton;

    public FrameErrorView(@NonNull Context context) {
        this(context, null);
    }

    public FrameErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.inline_error, this);
        ButterKnife.bind(this);
    }

    public View click() {
        return retryButton;
    }

    public TextView title() {
        return titleTextView;
    }

    public TextView body() {
        return bodyTextView;
    }
}
