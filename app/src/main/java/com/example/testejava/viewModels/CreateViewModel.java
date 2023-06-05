package com.example.testejava.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testejava.api.ApiResponse;
import com.example.testejava.model.Car;
import com.example.testejava.api.RetrofitClient;

import java.io.IOException;

import retrofit2.Response;

public class CreateViewModel extends ViewModel {

    public MutableLiveData<ApiResponse<Car>> saveCarResponse = new MutableLiveData<>();

    public void saveCar(Car car) {
        new Thread(() -> {
            try {
                Response response = RetrofitClient.getCarApi().saveCar(car).execute();
                handleSaveResponse(response, car);
            } catch (IOException e) {
                saveCarResponse.postValue(new ApiResponse.Failure<>("Error ao salvar o carro"));
            }
        }).start();
    }

    private void handleSaveResponse(Response response, Car car) {
        if (response.isSuccessful() && response.body() != null) {
            saveCarResponse.postValue(new ApiResponse.Success<>(car));
        } else {
            saveCarResponse.postValue(new ApiResponse.Failure<>("Error ao salvar o carro"));
        }
    }
}
