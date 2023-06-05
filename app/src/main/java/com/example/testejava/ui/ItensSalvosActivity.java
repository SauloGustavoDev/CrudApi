package com.example.testejava.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testejava.databinding.ActivityItensSalvosBinding;
import com.example.testejava.model.Car;
import com.example.testejava.ui.adapter.AdapterRecyclerViewSaveInCache;
import com.example.testejava.viewModels.ItensSalvosViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItensSalvosActivity extends AppCompatActivity implements AdapterRecyclerViewSaveInCache.OnItemClickListener {
    private ActivityItensSalvosBinding binding;
    AdapterRecyclerViewSaveInCache adapter;
    private ItensSalvosViewModel viewModel;
    List<Car> carrosSalvos = new ArrayList<>();
    //iniciando SharedPreferences para memoria em cache
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItensSalvosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ItensSalvosViewModel.class);
        this.preferences = getSharedPreferences("carros", Context.MODE_PRIVATE);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AdapterRecyclerViewSaveInCache(carrosSalvos, this);
        binding.recyclerView2.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.obterCarrosSalvos(preferences);

        viewModel.getCarrosLiveData().observe(this, cars -> adapter.setCarros(cars));
    }

    @Override
    public void onDeleteCar(int position) {
        viewModel.excluirCarro(position, preferences);
    }

    @Override
    public void onSaveItemClick(int position) {
        //Metodo que vai salvar o Modelo do carro em um arquivo interno no dispositivo
        boolean arquivoSalvo = viewModel.salvarArquivoInternamente("ItensArquivos", viewModel.getCarrosLiveData().getValue().get(position).getModel(), getApplicationContext());
        if (arquivoSalvo) {
            Toast.makeText(this, "Arquivo criado e salvo internamente com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Falha ao salvar o arquivo", Toast.LENGTH_SHORT).show();
        }

    }
}
