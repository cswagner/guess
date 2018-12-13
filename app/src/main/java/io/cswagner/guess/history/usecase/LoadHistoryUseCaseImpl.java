package io.cswagner.guess.history.usecase;

import android.support.annotation.Nullable;

import io.cswagner.guess.history.gateway.HistoryGateway;
import io.cswagner.guess.history.usecase.model.HistoryLoad;

public class LoadHistoryUseCaseImpl implements LoadHistoryUseCase {

    private HistoryGateway historyGateway;

    public LoadHistoryUseCaseImpl(HistoryGateway historyGateway) {
        this.historyGateway = historyGateway;
    }

    @Override
    public HistoryLoad load() {
        @Nullable Integer count = historyGateway.getCount();
        return count == null ? HistoryLoad.Failure.INSTANCE : new HistoryLoad.Success(count);
    }
}
