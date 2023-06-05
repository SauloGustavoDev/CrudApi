package com.example.testejava.api;

import com.example.testejava.model.Car;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CarApi {

    @POST("cars")
    Call<Car> saveCar(@Body Car car);

    @GET("cars")
    Call<List<Car>> getCars();

    @DELETE("cars/{id}")
    Call<ResponseBody> deleteCar(@Path("id") String carId);


    @PUT("cars/{id}")
    Call<ResponseBody> editCar(@Path("id") String carId, @Body Car car);
}
