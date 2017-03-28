package me.weeravit.guessnumber.network;

import me.weeravit.guessnumber.model.RandomRequest;
import me.weeravit.guessnumber.model.RandomResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by weeravit on 3/28/2017 AD.
 */

public interface ApiService {

    @POST("/")
    @Headers("Content-Type: application/json-rpc")
    Call<RandomResponse> getRandomNumber(@Body RandomRequest randomRequest);

}
