package io.cswagner.guess.history.ui.presenter;

import android.support.annotation.Nullable;

import io.cswagner.guess.common.Observer;
import io.cswagner.guess.history.ui.model.HistoryViewModel;

public class HistoryViewPresenterImpl implements HistoryViewPresenter {

    /* package */ static final HistoryViewModel DEFAULT_STATE = HistoryViewModel.Default.INSTANCE;
    @Nullable private Observer<HistoryViewModel> viewModelObserver;
    private HistoryViewModel currentState;

    public HistoryViewPresenterImpl() {
        this(null, DEFAULT_STATE);
    }

    /* package */ HistoryViewPresenterImpl(Observer<HistoryViewModel> viewModelObserver,
                                           HistoryViewModel initialState) {
        this.viewModelObserver = viewModelObserver;
        this.currentState = initialState;
    }

    @Override
    public void setViewModelObserver(Observer<HistoryViewModel> observer) {
        viewModelObserver = observer;
        emit(currentState);
    }

    @Override
    public void removeViewModelObserver() {
        viewModelObserver = null;
    }

    private void emit(HistoryViewModel viewModel) {
        if (viewModelObserver != null) {
            viewModelObserver.next(viewModel);
        }
    }
}
