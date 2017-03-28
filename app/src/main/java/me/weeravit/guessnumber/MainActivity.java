package me.weeravit.guessnumber;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private RandomResponse mRandomResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getRandomNumberByApi();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pgb_loading);
        mEdtGuess = (AppCompatEditText) findViewById(R.id.edt_guess);
        mBtnGuess = (AppCompatButton) findViewById(R.id.btn_confirm);
        mTextResultMessage = (AppCompatTextView) findViewById(R.id.text_result_message);

        mBtnGuess.setOnClickListener(onBtnGuessClicked);
    }

    private void getRandomNumberByApi() {
        mProgressBar.setVisibility(View.VISIBLE);

        HttpManager.getInstance()
                .getRandomNumber()
                .enqueue(new Callback<RandomResponse>() {
                    @Override
                    public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {
                        if (!isFinishing()) {
                            mRandomResponse = response.body();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<RandomResponse> call, Throwable t) {

                    }
                });
    }

    private View.OnClickListener onBtnGuessClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mRandomResponse != null) {
                int guessNumber = Integer.parseInt(mEdtGuess.getText().toString());
                String messageResult = mRandomResponse.getMessageResult(guessNumber);

                if (mRandomResponse.isCorrect(guessNumber)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage(messageResult)
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getRandomNumberByApi();
                                    mTextResultMessage.setText(getString(R.string.ready_to_guess));
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                } else {
                    mTextResultMessage.setText(messageResult);
                }
            }
        }
    };
}
