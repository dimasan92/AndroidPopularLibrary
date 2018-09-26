package ru.geekbrains.usefullibraries.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.usefullibraries.mvp.model.image.IImageLoader;
import ru.geekbrains.usefullibraries.mvp.model.image.android.GlideImageLoader;
import ru.geekbrains.usefullibraries.mvp.presenter.IReposListPresenter;
import ru.geekbrains.usefullibraries.mvp.presenter.MainPresenter;
import ru.geekbrains.usefullibraries.R;
import ru.geekbrains.usefullibraries.mvp.view.MainView;

public final class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;
    @BindView(R.id.rv_repos)
    RecyclerView reposRecyclerView;

    @InjectPresenter
    MainPresenter presenter;

    private IImageLoader<ImageView> imageLoader;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoader = new GlideImageLoader();
    }

    @Override
    public void setUsernameText(final String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(final String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void initList(final IReposListPresenter reposListPresenter) {
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reposRecyclerView.setAdapter(new ReposListAdapter(reposListPresenter));
        presenter.viewIsReady();
    }
}
