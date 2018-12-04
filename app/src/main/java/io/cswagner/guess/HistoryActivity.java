package io.cswagner.guess;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        sharedPreferences = getSharedPreferences("history", Context.MODE_PRIVATE);

        countTextView = findViewById(R.id.history_count);
        countTextView.setText(String.valueOf(sharedPreferences.getInt("count", 0)));
    }
}
