package io.cswagner.guess.history.gateway;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class HistoryGatewayImpl implements HistoryGateway {

    private static final String KEY_COUNT = "count";

    private SharedPreferences sharedPreferences;

    public HistoryGatewayImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    @Nullable
    public Integer getCount() {
        int count = sharedPreferences.getInt(KEY_COUNT, -1);
        return count == -1 ? null : count;
    }
}
