package io.cswagner.guess.history.ui.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

import io.cswagner.guess.common.Observer;
import io.cswagner.guess.history.ui.model.HistoryViewModel;

import static io.cswagner.guess.history.ui.presenter.HistoryViewPresenterImpl.DEFAULT_STATE;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HistoryViewPresenterTest {

    private Observer<HistoryViewModel> viewModelObserver;
    private HistoryViewPresenter presenter;
    private CountDownLatch countDownLatch = null;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewModelObserver = spy(new TestViewModelObserver());
    }

    @Test
    public void testDefaultState() throws InterruptedException {
        // given
        presenter = new HistoryViewPresenterImpl();

        // when
        expectEvents(1);
        presenter.setViewModelObserver(viewModelObserver);
        waitForEvents();

        // then
        verify(viewModelObserver, times(1)).next(DEFAULT_STATE);
    }

    @Test
    public void testInitialState() throws InterruptedException {
        // given
        HistoryViewModel initialState = new HistoryViewModel.Loaded(8);
        presenter = new HistoryViewPresenterImpl(null, initialState);

        // when
        expectEvents(1);
        presenter.setViewModelObserver(viewModelObserver);
        waitForEvents();

        // then
        verify(viewModelObserver, times(1)).next(initialState);
    }

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
