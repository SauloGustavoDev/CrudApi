package com.example.testejava.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testejava.api.ApiResponse;
import com.example.testejava.model.Car;
import com.example.testejava.api.RetrofitClient;

import java.io.IOException;

import retrofit2.Response;

public class EditViewModel extends ViewModel {

    MutableLiveData<ApiResponse<String>> messageError = new MutableLiveData<>();


    public void editCar(String id, Car car) {
        new Thread(() -> {
            try {
                Response response = RetrofitClient.getCarApi().editCar(id, car).execute();
                handleEditResponse(response, car);
            } catch (IOException e) {
                messageError.postValue(new ApiResponse.Failure<>("Error ao editar dados do carro"));
            }
        }).start();
    }

    private void handleEditResponse(Response response, Car car) {
        if (response.isSuccessful()) {
            messageError.postValue(new ApiResponse.Success(car));
        } else {
            messageError.postValue(new ApiResponse.Failure<>("Error ao salvar o carro"));
        }
    }
}
