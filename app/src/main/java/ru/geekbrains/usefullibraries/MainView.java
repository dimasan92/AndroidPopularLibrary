package ru.geekbrains.usefullibraries;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
interface MainView extends MvpView {

    void openImageChooser();

    void showConversion();

    void showSuccessConversionMessage();

    void showErrorConversionMessage();

    void showErrorLoadMessage();

    void showFileList();
}
