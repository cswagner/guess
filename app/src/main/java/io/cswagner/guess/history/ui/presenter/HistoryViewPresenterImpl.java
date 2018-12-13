package io.cswagner.guess.history.ui.presenter;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import io.cswagner.guess.common.Observer;
import io.cswagner.guess.history.ui.model.HistoryViewModel;
import io.cswagner.guess.history.usecase.LoadHistoryUseCase;
import io.cswagner.guess.history.usecase.model.HistoryLoad;

public class HistoryViewPresenterImpl implements HistoryViewPresenter {

    /* package */ static final HistoryViewModel DEFAULT_STATE = HistoryViewModel.Default.INSTANCE;

    private LoadHistoryUseCase loadHistoryUseCase;
    @Nullable private Observer<HistoryViewModel> viewModelObserver;
    private HistoryViewModel currentState;
    @Nullable private LoadHistoryTask loadHistoryTask;

    public HistoryViewPresenterImpl(LoadHistoryUseCase loadHistoryUseCase) {
        this(loadHistoryUseCase, null, DEFAULT_STATE);
    }

    /* package */ HistoryViewPresenterImpl(LoadHistoryUseCase loadHistoryUseCase,
                                           Observer<HistoryViewModel> viewModelObserver,
                                           HistoryViewModel initialState) {
        this.loadHistoryUseCase = loadHistoryUseCase;
        this.viewModelObserver = viewModelObserver;
        this.currentState = initialState;
        this.loadHistoryTask = null;
    }

    @Override
    public void setViewModelObserver(Observer<HistoryViewModel> observer) {
        viewModelObserver = observer;
        emit(currentState);

        loadHistory();
    }

    @Override
    public void removeViewModelObserver() {
        cancelHistoryLoad();
        viewModelObserver = null;
    }

    private static class LoadHistoryTask extends AsyncTask<Void, Void, HistoryLoad> {

        private WeakReference<HistoryViewPresenterImpl> presenter;
        private LoadHistoryUseCase useCase;

        private LoadHistoryTask(HistoryViewPresenterImpl presenter, LoadHistoryUseCase useCase) {
            this.presenter = new WeakReference<>(presenter);
            this.useCase = useCase;
        }

        @Override
        protected void onPreExecute() {
            if (presenter.get() != null) {
                presenter.get().reconcileHistoryLoad(isCancelled()
                        ? HistoryLoad.Cancelled.INSTANCE
                        : HistoryLoad.InProgress.INSTANCE);
            }
        }

        @Override
        protected HistoryLoad doInBackground(Void... ignore) {
            if (isCancelled()) {
                return HistoryLoad.Cancelled.INSTANCE;
            }

            HistoryLoad load = useCase.load();
            return isCancelled() ? HistoryLoad.Cancelled.INSTANCE : load;
        }

        @Override
        protected void onPostExecute(HistoryLoad load) {
            if (presenter.get() != null) {
                presenter.get().reconcileHistoryLoad(isCancelled()
                        ? HistoryLoad.Cancelled.INSTANCE
                        : load);
                presenter.get().loadHistoryTask = null;
            }
        }

        @Override
        protected void onCancelled(HistoryLoad load) {
            if (presenter.get() != null) {
                presenter.get().reconcileHistoryLoad(HistoryLoad.Cancelled.INSTANCE);
                presenter.get().loadHistoryTask = null;
            }
        }
    }

    private void loadHistory() {
        if (loadHistoryTask == null) {
            loadHistoryTask = new LoadHistoryTask(this, loadHistoryUseCase);
            loadHistoryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void cancelHistoryLoad() {
        if (loadHistoryTask != null) {
            loadHistoryTask.cancel(true);
            loadHistoryTask = null;
        }
    }

    private void reconcileHistoryLoad(HistoryLoad load) {
        @Nullable HistoryViewModel newState = null;

        if (load instanceof HistoryLoad.InProgress
                && !(currentState instanceof HistoryViewModel.Loading)) {
            newState = HistoryViewModel.Loading.INSTANCE;
        } else if (currentState instanceof HistoryViewModel.Loading) {
            if (load instanceof HistoryLoad.Success) {
                newState = new HistoryViewModel.Loaded(((HistoryLoad.Success)load).count);
            } else if (load instanceof HistoryLoad.Failure) {
                newState = HistoryViewModel.Error.INSTANCE;
            } else if (load instanceof HistoryLoad.Cancelled) {
                newState = HistoryViewModel.Default.INSTANCE;
            }
        }

        if (newState != null) {
            currentState = newState;
            emit(currentState);
        }
    }

    private void emit(HistoryViewModel viewModel) {
        if (viewModelObserver != null) {
            viewModelObserver.next(viewModel);
        }
    }
}
