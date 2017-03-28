package me.weeravit.guessnumber.network;

import me.weeravit.guessnumber.model.RandomRequest;
import me.weeravit.guessnumber.model.RandomResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by weeravit on 3/28/2017 AD.
 */

public class HttpManager {

    private static final HttpManager ourInstance = new HttpManager();

    private ApiService mService;

    public static HttpManager getInstance() {
        return ourInstance;
    }

    private HttpManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .build();
        mService = retrofit.create(ApiService.class);
    }

    public Call<RandomResponse> getRandomNumber() {
        RandomRequest randomRequest = RandomRequest.create();
        return mService.getRandomNumber(randomRequest);
    }

}
