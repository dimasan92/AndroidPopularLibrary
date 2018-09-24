package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.InputStream;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private final Scheduler uiScheduler;
    private final MainModel model;
    private Disposable interrupter;

    MainPresenter(Scheduler uiScheduler, ImageConverter converter) {
        this.uiScheduler = uiScheduler;
        model = new MainModel(converter);
    }

    void onConvertClick() {
        getViewState().openImageChooser();
    }

    void successReceivedImageData(InputStream dataStream) {
        getViewState().showConversion();
        interrupter = model.convertImage(dataStream)
                .observeOn(uiScheduler)
                .subscribe(success -> {
                    if (success) {
                        getViewState().showSuccessConversionMessage();
                    } else {
                        getViewState().showErrorConversionMessage();
                    }
                }, throwable -> getViewState().showErrorConversionMessage());
    }

    void cancelConverting() {
        interrupter.dispose();
    }

    void errorReceivedImageData() {
        getViewState().showErrorLoadMessage();
    }

    void onFileListClick() {
        getViewState().showFileList();
    }
}
