package io.cswagner.guess.history.usecase.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HistoryLoadTest {

    @Parameterized.Parameter
    public HistoryLoad load1;

    @Parameterized.Parameter(value = 1)
    public HistoryLoad load2;

    @Parameterized.Parameter(value = 2)
    public boolean expected;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                {
                    HistoryLoad.InProgress.INSTANCE,
                    HistoryLoad.InProgress.INSTANCE,
                    true
                },
                {
                    HistoryLoad.InProgress.INSTANCE,
                    new HistoryLoad.Success(3),
                    false
                },
                {
                    HistoryLoad.InProgress.INSTANCE,
                    HistoryLoad.Failure.INSTANCE,
                    false
                },
                {
                    HistoryLoad.InProgress.INSTANCE,
                    HistoryLoad.Cancelled.INSTANCE,
                    false
                },
                {
                    new HistoryLoad.Success(5),
                    new HistoryLoad.Success(5),
                    true
                },
                {
                    new HistoryLoad.Success(5),
                    new HistoryLoad.Success(9),
                    false
                },
                {
                    new HistoryLoad.Success(1),
                    HistoryLoad.Failure.INSTANCE,
                    false
                },
                {
                    new HistoryLoad.Success(2),
                    HistoryLoad.Cancelled.INSTANCE,
                    false
                },
                {
                    HistoryLoad.Failure.INSTANCE,
                    HistoryLoad.Failure.INSTANCE,
                    true
                },
                {
                    HistoryLoad.Failure.INSTANCE,
                    HistoryLoad.Cancelled.INSTANCE,
                    false
                },
                {
                    HistoryLoad.Cancelled.INSTANCE,
                    HistoryLoad.Cancelled.INSTANCE,
                    true
                }
        });
    }

    @Test
    public void testEquality() {
        assertEquals(expected, load1.equals(load2));
        assertEquals(expected, load2.equals(load1));
        assertEquals(expected, load1.hashCode() == load2.hashCode());
    }
}
