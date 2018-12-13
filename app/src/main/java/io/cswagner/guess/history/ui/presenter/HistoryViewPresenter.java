package io.cswagner.guess.history.ui.presenter;

import io.cswagner.guess.common.Observer;
import io.cswagner.guess.history.ui.model.HistoryViewModel;

public interface HistoryViewPresenter {

    void setViewModelObserver(Observer<HistoryViewModel> observer);
    void removeViewModelObserver();
}
