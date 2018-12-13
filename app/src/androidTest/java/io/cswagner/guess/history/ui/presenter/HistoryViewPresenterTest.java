package io.cswagner.guess.history.ui.presenter;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import io.cswagner.guess.common.Observer;
import io.cswagner.guess.history.ui.model.HistoryViewModel;
import io.cswagner.guess.history.usecase.LoadHistoryUseCase;

import static org.mockito.Mockito.spy;

public class HistoryViewPresenterTest {

    @Mock private LoadHistoryUseCase loadHistoryUseCase;
    private Observer<HistoryViewModel> viewModelObserver;
    private HistoryViewPresenter presenter;
    private CountDownLatch countDownLatch = null;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModelObserver = spy(new TestViewModelObserver());
    }

    // TODO: add tests

    public class TestViewModelObserver implements Observer<HistoryViewModel> {

        @Override
        public void next(HistoryViewModel item) {
            reportEvent();
        }
    }

    private void expectEvents(int count) {
        countDownLatch = new CountDownLatch(count);
    }

    private void reportEvent() {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    private void waitForEvents() throws InterruptedException {
        if (countDownLatch != null) {
            countDownLatch.await();
        }
    }
}
