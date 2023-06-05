package com.example.testejava.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testejava.api.ApiResponse;
import com.example.testejava.api.RetrofitClient;
import com.example.testejava.model.Car;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class MainViewModel extends ViewModel {
   public MutableLiveData<ApiResponse<List<Car>>> getCarsResponse = new MutableLiveData<>();
   public MutableLiveData<ApiResponse<String>> statusMessageDelete = new MutableLiveData<>();


    public void getCars() {
        new Thread(() -> {
            try {
                Response response = RetrofitClient.getCarApi().getCars().execute();
                handleGetCars(response);
            } catch (IOException e) {
                getCarsResponse.postValue(new ApiResponse.Failure<>("Error ao buscar carros"));
            }
        }).start();
    }
    public void deleteCar(String id){
        new Thread(() ->{
            try {
                Response response = RetrofitClient.getCarApi().deleteCar(id).execute();
                handleDeleteCars(response);
            } catch (Exception e) {
            statusMessageDelete.postValue(new ApiResponse.Failure<>("Error ao deletar carro"));
        }
        }).start();
    }
    private void handleDeleteCars(Response response){
        if (response.isSuccessful()) {
            statusMessageDelete.postValue(new ApiResponse.Success(response.body()));
        } else {
            statusMessageDelete.postValue(new ApiResponse.Failure<>("Error ao deletar carro"));
        }
    }
    private void handleGetCars(Response response) {
        if (response.isSuccessful() && response.body() != null) {
            getCarsResponse.postValue(new ApiResponse.Success(response.body()));
        } else {
            getCarsResponse.postValue(new ApiResponse.Failure<>("Error ao buscar carros"));
        }
    }

}
