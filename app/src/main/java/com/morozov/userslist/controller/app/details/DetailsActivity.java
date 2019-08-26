package com.morozov.userslist.controller.app.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.morozov.userslist.R;
import com.morozov.userslist.controller.ControllerActivity;
import com.morozov.userslist.controller.app.users.UsersActivity;
import com.morozov.userslist.controller.app.users.UsersAdapter;
import com.morozov.userslist.models.EyeColor;
import com.morozov.userslist.models.FavoriteFruit;
import com.morozov.userslist.models.UserModel;
import com.morozov.userslist.utility.AppNavigation;
import com.morozov.userslist.utility.RegistrationTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.morozov.userslist.controller.app.details.DetailsComponent.DETAILS_COMPONENT;

public class DetailsActivity extends ControllerActivity<DetailsViewModel, DetailsController, DetailsComponent> {

    @BindView(R.id.profileScrollView)
    ScrollView scrollView;

    @BindView(R.id.nameText)
    TextView nameTextView;
    @BindView(R.id.yoText)
    TextView ageTextView;
    @BindView(R.id.companyText)
    TextView companyTextView;
    @BindView(R.id.addressText)
    TextView addressTextView;
    @BindView(R.id.registeredText)
    TextView registeredTextView;
    @BindView(R.id.aboutText)
    TextView aboutTextView;
    @BindView(R.id.eyeColorImage)
    ImageView eyeColorImageView;
    @BindView(R.id.favoriteFruitImage)
    ImageView favoriteFruitImageView;

    @BindView(R.id.emailClickable)
    TextView clickableEmail;
    @BindView(R.id.phoneClickable)
    TextView clickablePhone;
    @BindView(R.id.locationClickable)
    TextView clickableLocation;

    @BindView(R.id.recyclerView_users_list)
    RecyclerView recyclerView;

    @BindView(R.id.details_activity_progress_bar)
    ProgressBar progressBar;

    UsersAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        adapter = new UsersAdapter(getApplicationContext(), controller);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        observeClicks(clickableEmail, clickablePhone, clickableLocation);
    }

    @Override
    protected void observe(DetailsViewModel viewModel) {
        super.observe(viewModel);

        viewModel.friends().observe(this, userModels -> {
            adapter.setData(userModels);
            adapter.notifyDataSetChanged();
        });

        viewModel.user().observe(this, this::showUserInfo);

        viewModel.selectedFriend().observe(this, integer -> {
            if (AppNavigation.getInstance(getApplicationContext()).getUsersStack() == null)
                AppNavigation.getInstance(getApplicationContext())
                        .setUsersStack(new ArrayList<>());

            AppNavigation.getInstance(getApplicationContext()).getUsersStack()
                    .add(AppNavigation.getInstance(getApplicationContext()).getCurrentUser());

            AppNavigation.getInstance(getApplicationContext()).setCurrentUser(integer);
            AppNavigation.invokeNewActivity(DetailsActivity.this, DetailsActivity.class, true);
        });

        viewModel.showProgress().observe(this, aBoolean -> {
            if (aBoolean){
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });


        viewModel.gotoEmail().observe(this, b -> {
            if (b == null || !b)
                return;

            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", viewModel.user().getValue().getEmail(), null));

            if (intent.resolveActivity(Objects.requireNonNull(getApplicationContext()).getPackageManager()) != null) {
                startActivity(intent);
                viewModel.gotoEmail().setValue(null);
            }
        });

        viewModel.gotoPhone().observe(this, b -> {
            if (b == null || !b)
                return;

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + viewModel.user().getValue().getPhone()));

            if (intent.resolveActivity(Objects.requireNonNull(getApplicationContext()).getPackageManager()) != null) {
                startActivity(intent);
                viewModel.gotoPhone().setValue(null);
            }
        });

        viewModel.gotoLocation().observe(this, b -> {
            if (b == null || !b)
                return;

            Uri gmmIntentUri = Uri.parse("geo:"
                    + viewModel.user().getValue().getLatitude() + "," + viewModel.user().getValue().getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            if (intent.resolveActivity(Objects.requireNonNull(getApplicationContext()).getPackageManager()) != null) {
                startActivity(intent);
                viewModel.gotoLocation().setValue(null);
            }
        });
    }

    private void showUserInfo(UserModel user) {
        scrollView.smoothScrollTo(0, 0);

        nameTextView.setText(user.getName());
        ageTextView.setText(String.format(getString(R.string.age), user.getAge()));
        companyTextView.setText(String.format(getString(R.string.company), user.getCompany()));
        addressTextView.setText(String.format(getString(R.string.address), user.getAddress()));
        registeredTextView.setText(String.format(
                getString(R.string.date_of_registration), RegistrationTime.getFormattedDate(user.getRegistered())));
        aboutTextView.setText(String.format(getString(R.string.about_user), user.getAbout()));

        clickableEmail.setText(highlightLink(user.getEmail()));
        clickablePhone.setText(highlightLink(user.getPhone()));
        clickableLocation.setText(highlightLink(user.getLatitude() + ", " + user.getLongitude()));

        Drawable background = eyeColorImageView.getBackground();
        if (background instanceof GradientDrawable) {
            EyeColor eyeColor = EyeColor.parseColor(user.getEyeColor());
            int drawableId = ContextCompat.getColor(getApplicationContext(), eyeColor.getDrawableId());
            ((GradientDrawable) background).setColor(drawableId);
        }

        FavoriteFruit favoriteFruit = FavoriteFruit.parseFruit(user.getFavoriteFruit());
        favoriteFruitImageView.setBackgroundResource(favoriteFruit.getDrawableId());
    }

    private Spannable highlightLink(String str) {
        int spanConst = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
        Spannable text = new SpannableString(str);
        text.setSpan(new UnderlineSpan(), 0, str.length(), spanConst);
        int color = ContextCompat.getColor(getApplicationContext(), R.color.linkColor);
        text.setSpan(new ForegroundColorSpan(color), 0, str.length(), spanConst);
        return text;
    }

    @Override
    protected DetailsController createController(DetailsViewModel viewModel) {
        DetailsController detailsController = new DetailsController(viewModel);
        detailsController.setContext(getApplicationContext());
        injector(DETAILS_COMPONENT).inject(detailsController);
        return detailsController;
    }

    @Override
    protected Class<DetailsViewModel> viewModel() {
        return DetailsViewModel.class;
    }

    @Override
    public void onBackPressed() {
        if (AppNavigation.getInstance(getApplicationContext()).getUsersStack() == null) {
            AppNavigation.invokeNewActivity(DetailsActivity.this, UsersActivity.class, true);
        } else {
            List<Integer> usersStack = AppNavigation.getInstance(getApplicationContext())
                    .getUsersStack();
            AppNavigation.getInstance(getApplicationContext())
                    .setCurrentUser(usersStack.get(usersStack.size() - 1));
            usersStack.remove(usersStack.size() - 1);

            if (usersStack.size() < 1)
                AppNavigation.getInstance(getApplicationContext())
                .setUsersStack(null);

            AppNavigation.invokeNewActivity(DetailsActivity.this, DetailsActivity.class, true);
        }
    }
}
