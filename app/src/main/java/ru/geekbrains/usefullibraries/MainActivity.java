package ru.geekbrains.usefullibraries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.FileNotFoundException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int IMAGE_REQUEST_CODE = 1;

    @BindView(R.id.btn_convert_image)
    Button convertButton;
    @BindView(R.id.parent_container)
    RelativeLayout parentLayout;
    @BindView(R.id.btn_file_list)
    Button fileListButton;
    @BindView(R.id.tv_file_list)
    TextView fileList;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(AndroidSchedulers.mainThread(),
                new ImageConverterImpl(this.getApplicationContext()));
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
            try {
                presenter.successReceivedImageData(
                        getContentResolver().openInputStream(data.getData()));
            } catch (FileNotFoundException | NullPointerException e) {
                presenter.errorReceivedImageData();
            }
        }
    }

    @Override
    public void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, "image/jpeg");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_file)),
                IMAGE_REQUEST_CODE);
    }

    @Override
    public void showConversion() {
        Snackbar.make(parentLayout, R.string.image_is_converting, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.cancel_converting, v -> presenter.cancelConverting()).show();
    }

    @Override
    public void showErrorLoadMessage() {
        Snackbar.make(parentLayout, R.string.error_load_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessConversionMessage() {
        Snackbar.make(parentLayout, R.string.success_convert, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorConversionMessage() {
        Snackbar.make(parentLayout, R.string.error_convert, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFileList() {
        fileList.setText(Arrays.toString(fileList()));
    }

    @OnClick(R.id.btn_convert_image)
    void onConvertButtonClick() {
        presenter.onConvertClick();
    }

    @OnClick(R.id.btn_file_list)
    void onFileListButtonClick() {
        presenter.onFileListClick();
    }
}
