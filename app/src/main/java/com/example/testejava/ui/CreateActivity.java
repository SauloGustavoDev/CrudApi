package com.example.testejava.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.testejava.api.ApiResponse;
import com.example.testejava.databinding.ActivityCreateBinding;
import com.example.testejava.model.Car;
import com.example.testejava.viewModels.CreateViewModel;

public class CreateActivity extends AppCompatActivity {
    private ActivityCreateBinding binding;
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        //Ação de clique FAB cancelar criação
        binding.fabCreateCancel.setOnClickListener(view -> finalizaActivity());

        //Ação de clique FAB confirmar criação
        binding.fabCreateConfirm.setOnClickListener(view -> createCar());

    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.saveCarResponse.observe(this, value -> {
            if (value instanceof ApiResponse.Success) {
                Toast.makeText(this, "Veiculo criado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Falha ao criar veiculo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finalizaActivity() {
        finish();
        onBackPressed();
    }

    private void createCar() {
        if (!binding.edtModelo.getText().toString().isEmpty() && !binding.edtValor.getText().toString().isEmpty() && !binding.edtAno.getText().toString().isEmpty() && !binding.edtDescricao.getText().toString().isEmpty()) {
            Car car = new Car(binding.edtModelo.getText().toString(), binding.edtValor.getText().toString(), binding.edtAno.getText().toString(), binding.edtDescricao.getText().toString());
            viewModel.saveCar(car);
            finalizaActivity();
        } else {
            Toast.makeText(this, "Preencha todos os dados do veiculo!", Toast.LENGTH_SHORT).show();
        }
    }
}