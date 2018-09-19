package ru.geekbrains.usefullibraries;

import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.btn_one)
    Button buttonOne;
    @BindView(R.id.btn_two)
    Button buttonTwo;
    @BindView(R.id.btn_three)
    Button buttonThree;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter();
        return presenter;
    }

    @OnClick(R.id.btn_one)
    public void onButtonOneClick(Button button) {
        presenter.counterButtonOneClick();
    }

    @OnClick(R.id.btn_two)
    public void onButtonTwoClick(Button button) {
        presenter.counterButtonTwoClick();
    }

    @OnClick(R.id.btn_three)
    public void onButtonThreeClick(Button button) {
        presenter.counterButtonThreeClick();
    }

    @Override
    public void setButtonOneText(int val) {
        buttonOne.setText(String.format(Locale.getDefault(), "%d", val));
    }

    @Override
    public void setButtonTwoText(int val) {
        buttonTwo.setText(String.format(Locale.getDefault(), "%d", val));
    }

    @Override
    public void setButtonThreeText(int val) {
        buttonThree.setText(String.format(Locale.getDefault(), "%d", val));
    }
}
