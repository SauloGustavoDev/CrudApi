package com.example.testejava.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String URL = "https://crudcrud.com/api/989ddabdc79c4925a5a59f50b00685fc/";

    private static Retrofit retrofit;

    private RetrofitClient() {
        // Construtor privado para evitar instanciação externa
    }

    private static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CarApi getCarApi() {
        return getInstance().create(CarApi.class);
    }
}
