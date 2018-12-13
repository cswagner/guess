package io.cswagner.guess.history.ui.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HistoryViewModelTest {

    @Parameterized.Parameter
    public HistoryViewModel viewModel1;

    @Parameterized.Parameter(value = 1)
    public HistoryViewModel viewModel2;

    @Parameterized.Parameter(value = 2)
    public boolean expected;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                {
                    HistoryViewModel.Default.INSTANCE,
                    HistoryViewModel.Default.INSTANCE,
                    true
                },
                {
                    HistoryViewModel.Default.INSTANCE,
                    HistoryViewModel.Loading.INSTANCE,
                    false
                },
                {
                    HistoryViewModel.Default.INSTANCE,
                    new HistoryViewModel.Loaded(13),
                    false
                },
                {
                    HistoryViewModel.Default.INSTANCE,
                    HistoryViewModel.Error.INSTANCE,
                    false
                },
                {
                    HistoryViewModel.Loading.INSTANCE,
                    HistoryViewModel.Loading.INSTANCE,
                    true
                },
                {
                    HistoryViewModel.Loading.INSTANCE,
                    new HistoryViewModel.Loaded(3),
                    false
                },
                {
                    HistoryViewModel.Loading.INSTANCE,
                    HistoryViewModel.Error.INSTANCE,
                    false
                },
                {
                    new HistoryViewModel.Loaded(1),
                    new HistoryViewModel.Loaded(1),
                    true
                },
                {
                    new HistoryViewModel.Loaded(1),
                    new HistoryViewModel.Loaded(4),
                    false
                },
                {
                    new HistoryViewModel.Loaded(6),
                    HistoryViewModel.Error.INSTANCE,
                    false
                },
                {
                    HistoryViewModel.Error.INSTANCE,
                    HistoryViewModel.Error.INSTANCE,
                    true
                }
        });
    }

    @Test
    public void testEquality() {
        assertEquals(expected, viewModel1.equals(viewModel2));
        assertEquals(expected, viewModel2.equals(viewModel1));
        assertEquals(expected, viewModel1.hashCode() == viewModel2.hashCode());
    }
}
