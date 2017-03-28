package me.weeravit.guessnumber;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    private AppCompatEditText mEdtGuess;
    private AppCompatButton mBtnGuess;
    private AppCompatTextView mTextResultMessage;
    private RandomResponse mRandomResponse;
    private ProgressDialog mProgressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initInstance();
        getRandomNumberByApi();
    }

    private void initInstance() {
        mProgressLoading = new ProgressDialog(this);
        mProgressLoading.setMessage("Requesting Random number");
        mProgressLoading.setIndeterminate(true);
        mProgressLoading.setCancelable(false);
    }

    private void initView() {
        mEdtGuess = (AppCompatEditText) findViewById(R.id.edt_guess);
        mBtnGuess = (AppCompatButton) findViewById(R.id.btn_confirm);
        mTextResultMessage = (AppCompatTextView) findViewById(R.id.text_result_message);

        mBtnGuess.setOnClickListener(onBtnGuessClicked);
    }

    private void getRandomNumberByApi() {
        mProgressLoading.show();

        HttpManager.getInstance()
                .getRandomNumber()
                .enqueue(new Callback<RandomResponse>() {
                    @Override
                    public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {
                        if (!isFinishing()) {
                            mRandomResponse = response.body();
                            mProgressLoading.dismiss();
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
