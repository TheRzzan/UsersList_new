package com.morozov.userslist.controller;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.morozov.userslist.controller.interaction.DefaultViewClick;
import com.morozov.userslist.controller.interaction.ViewClick;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public abstract class ControllerActivity<V extends ViewModel, C extends Controller, DI extends ControllerComponent>
        extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private V viewModel;
    private C controller;

    @SuppressWarnings("unchecked")
    protected DI injector(String name) {
        return (DI) getApplication().getSystemService(name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(viewModel());
        controller = createController(viewModel);
        getLifecycle().addObserver(controller);
    }

    @Override
    protected void onStart() {
        super.onStart();

        observe(viewModel);
    }

    protected void observe(V viewModel) {

    }

    protected void observeClicks(View... views) {
        for (View view : views) {
            observeClicks(view);
        }
    }

    @SuppressLint("CheckResult")
    protected void observeClicks(final View view) {
        RxView.clicks(view).flatMap(new Function<Object, ObservableSource<ViewClick>>() {
            @Override
            public ObservableSource<ViewClick> apply(@io.reactivex.annotations.NonNull Object o) throws Exception {
                return new ObservableSource<ViewClick>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull Observer<? super ViewClick> observer) {
                        observer.onNext(new DefaultViewClick(view.getId()));
                    }
                };
            }
        }).subscribe(controller.click());
    }

    protected abstract C createController(V viewModel);

    protected abstract Class<V> viewModel();

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
