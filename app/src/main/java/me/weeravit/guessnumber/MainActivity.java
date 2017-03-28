package me.weeravit.guessnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.weeravit.guessnumber.model.RandomRequest;
import me.weeravit.guessnumber.model.RandomResponse;
import me.weeravit.guessnumber.network.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
        getRandomNumberByApi();
    }

    private void initInstances() {

    }

    private void getRandomNumberByApi() {
        HttpManager.getInstance()
                .getRandomNumber()
                .enqueue(new Callback<RandomResponse>() {
            @Override
            public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {

            }

            @Override
            public void onFailure(Call<RandomResponse> call, Throwable t) {

            }
        });
    }

}
