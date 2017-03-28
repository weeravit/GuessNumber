package me.weeravit.guessnumber;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;

import me.weeravit.guessnumber.model.RandomResponse;
import me.weeravit.guessnumber.network.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private AppCompatEditText mEdtGuess;
    private AppCompatButton mBtnGuess;
    private AppCompatTextView mTextResultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pgb_loading);
        mEdtGuess = (AppCompatEditText) findViewById(R.id.edt_guess);
        mBtnGuess = (AppCompatButton) findViewById(R.id.btn_confirm);
        mTextResultMessage = (AppCompatTextView) findViewById(R.id.text_result_message);

        mBtnGuess.setOnClickListener(onBtnGuessClicked);
    }

    private void getRandomNumberByApi(final int guessNumber) {
        mProgressBar.setVisibility(View.VISIBLE);

        HttpManager.getInstance()
                .getRandomNumber()
                .enqueue(new Callback<RandomResponse>() {
                    @Override
                    public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {
                        RandomResponse randomResponse = response.body();

                        mProgressBar.setVisibility(View.GONE);
                        mTextResultMessage.setText(randomResponse.getMessageResult(guessNumber));
                    }

                    @Override
                    public void onFailure(Call<RandomResponse> call, Throwable t) {

                    }
                });
    }

    private View.OnClickListener onBtnGuessClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int guessNumber = Integer.parseInt(mEdtGuess.getText().toString());
            getRandomNumberByApi(guessNumber);
        }
    };
}
