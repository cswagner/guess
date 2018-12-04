package io.cswagner.guess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private TextView textView;
    private EditText inputTextView;
    private Button inputButton;
    private Button replayButton;
    private Button historyButton;

    private int num = randomInt();
    private int guesses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedPreferences = getSharedPreferences("history", Context.MODE_PRIVATE);

        textView = findViewById(R.id.main_text);
        textView.setText("I'm thinking of a number between 1 and 100");

        inputTextView = findViewById(R.id.main_input_text);
        inputTextView.setVisibility(VISIBLE);
        inputTextView.getEditableText().clear();

        inputButton = findViewById(R.id.main_input_button);
        inputButton.setVisibility(VISIBLE);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int guess = Integer.parseInt(inputTextView.getText().toString());
                guesses++;

                if (guess < 1) {
                    textView.setText("I'm not thinking of a number that low. Try again");
                } else if (guess > 100) {
                    textView.setText("I'm not thinking of a number that high. Try again");
                } else if (guess > num) {
                    textView.setText(guess + " is too high. Try again");
                } else if (guess < num) {
                    textView.setText(guess + " is too low. Try again");
                } else {
                    int gamesPlayed = sharedPreferences.getInt("count", 0);
                    sharedPreferences.edit().putInt("count", gamesPlayed + 1).apply();

                    textView.setText("My number was " + guess
                            + ". You got it in " + guesses + (guesses > 1 ? " guesses." : " guess."));
                    inputTextView.setVisibility(GONE);
                    inputButton.setVisibility(GONE);
                    replayButton.setVisibility(VISIBLE);
                    historyButton.setVisibility(VISIBLE);
                }

                inputTextView.getEditableText().clear();
            }
        });

        replayButton = findViewById(R.id.main_replay_button);
        replayButton.setVisibility(GONE);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = randomInt();
                guesses = 0;

                replayButton.setVisibility(GONE);
                historyButton.setVisibility(GONE);
                textView.setText("I'm thinking of a number between 1 and 100");
                inputTextView.getEditableText().clear();
                inputTextView.setVisibility(VISIBLE);
                inputButton.setVisibility(VISIBLE);
            }
        });

        historyButton = findViewById(R.id.main_history_button);
        historyButton.setVisibility(GONE);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }

    private int randomInt() {
        return 1 + (int)(Math.random() * (100 - 1));
    }
}
