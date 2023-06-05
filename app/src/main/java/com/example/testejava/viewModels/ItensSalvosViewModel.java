package com.example.testejava.viewModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testejava.model.Car;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItensSalvosViewModel extends ViewModel {
    private List<Car> carros;
    private MutableLiveData<List<Car>> carrosLiveData;


    public ItensSalvosViewModel() {
        carros = new ArrayList<>();
        carrosLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Car>> getCarrosLiveData() {
        return carrosLiveData;
    }

    public void excluirCarro(int position, SharedPreferences preferences) {
        Car carro = carros.get(position);
        carros.remove(position);

        SharedPreferences.Editor editor = preferences.edit();
        String chave = "carro_" + carro.get_id();
        editor.remove(chave);
        editor.apply();

        // Notifique os observadores que a lista de carros foi alterada
        carrosLiveData.setValue(carros);
    }

    public void obterCarrosSalvos(SharedPreferences preferences) {
        Map<String, ?> allEntries = preferences.getAll();

        Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String jsonString = entry.getValue().toString();
            Car carro = gson.fromJson(jsonString, Car.class);
            carros.add(carro);
        }

        carrosLiveData.setValue(carros);
    }

    public boolean salvarArquivoInternamente(String nomeArquivo, String conteudo, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
            fos.write(conteudo.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
