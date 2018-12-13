package io.cswagner.guess.history;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.cswagner.guess.history.gateway.HistoryGateway;
import io.cswagner.guess.history.usecase.LoadHistoryUseCase;
import io.cswagner.guess.history.usecase.LoadHistoryUseCaseImpl;
import io.cswagner.guess.history.usecase.model.HistoryLoad;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

public class LoadHistoryUseCaseTest {

    @Mock private HistoryGateway historyGateway;

    private LoadHistoryUseCase useCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        useCase = new LoadHistoryUseCaseImpl(historyGateway);
    }

    @Test
    public void testFailure() {
        // given
        given(historyGateway.getCount()).willReturn(null);

        // when
        HistoryLoad load = useCase.load();

        // then
        assertEquals(HistoryLoad.Failure.INSTANCE, load);
    }

    @Test
    public void testSuccess() {
        // given
        int count = 13;
        given(historyGateway.getCount()).willReturn(count);

        // when
        HistoryLoad load = useCase.load();

        // then
        assertEquals(new HistoryLoad.Success(count), load);
    }
}
