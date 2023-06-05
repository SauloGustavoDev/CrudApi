package com.example.testejava.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testejava.api.ApiResponse;
import com.example.testejava.databinding.ActivityMainBinding;
import com.example.testejava.model.Car;
import com.example.testejava.ui.adapter.AdapterRecyclerView;
import com.example.testejava.viewModels.MainViewModel;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements onMenuClick {
    final String carKey = "carKey";
    private MainViewModel viewModel;
    AdapterRecyclerView adapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //Configurando Recycler
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Ação de click no FAB para iniciar a CreateActivity
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });

        //Ação de click no Fab para iniciar a SaveActivity
        binding.fabSaves.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ItensSalvosActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getCars();
        reloadActivity();

        viewModel.getCarsResponse.observe(this, value -> {
            if (value instanceof ApiResponse.Success) {
                adapter = new AdapterRecyclerView(this, value.getData());
                binding.recyclerView.setAdapter(adapter);
                binding.warningList.setVisibility(View.GONE);
            } else {
                binding.warningList.setVisibility(View.VISIBLE);
            }
        });


        viewModel.statusMessageDelete.observe(this, value -> {
            if (value instanceof ApiResponse.Success) {
                Toast.makeText(this, "Veiculo deletado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Falha ao deletar veiculo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getCars();
    }

    @Override
    public void onEdit(Car car) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(carKey, car);
        startActivity(intent);

    }

    @Override
    public void onSaveinCache(Car car) {
        SharedPreferences preferences = getSharedPreferences("carros", 0);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String jsonCarro = gson.toJson(car);

        editor.putString("carro_" + car.get_id(), jsonCarro);
        editor.apply();

    }

    @Override
    public void onDelete(String id) {
        viewModel.deleteCar(id);
    }

    //Metodo para atualizar a lista de veiculos a cada 4Segundos
    private void reloadActivity() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Chame o método que deseja executar repetidamente
                viewModel.getCars();
                // Agende a próxima chamada após 4 segundos
                handler.postDelayed(this, 7000);
            }
        };
        handler.post(runnable);

    }
}
