package com.example.testejava.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.testejava.model.Car;
import com.example.testejava.viewModels.EditViewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.testejava.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private EditViewModel viewModel;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditViewModel.class);

        //Recuperando carro selecionado
        Intent intent = getIntent();
        car = (Car) intent.getSerializableExtra("carKey");


        binding.fabEditConfirm.setOnClickListener(view -> editCar());

        binding.fabEditCancel.setOnClickListener(view -> finalizaActivity());


    }

    @Override
    protected void onStart() {
        super.onStart();

        //Setando os campos com os dados do carro
        setaCamposEdtText();
    }

    private void setaCamposEdtText() {
        binding.edtModelo.setText(car.getModel());
        binding.edtValor.setText(car.getPrice());
        binding.edtAno.setText(car.getYear());
        binding.edtDescricao.setText(car.getDescription());
    }

    private void setaCamposCarro() {
        car.setModel(binding.edtModelo.getText().toString());
        car.setPrice(binding.edtValor.getText().toString());
        car.setYear(binding.edtAno.getText().toString());
        car.setDescription(binding.edtDescricao.getText().toString());
    }

    private void finalizaActivity() {
        finish();
        onBackPressed();
    }

    private void editCar() {
        if (!binding.edtModelo.getText().toString().isEmpty() && !binding.edtValor.getText().toString().isEmpty() && !binding.edtAno.getText().toString().isEmpty() && !binding.edtDescricao.getText().toString().isEmpty()) {
            String idCar = car.get_id();
            car.set_id(null);
            setaCamposCarro();
            viewModel.editCar(idCar, car);
            Toast.makeText(EditActivity.this, "Carro Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finalizaActivity();
        } else {
            Toast.makeText(EditActivity.this, "Preencha todos os dados do veiculo!", Toast.LENGTH_SHORT).show();
        }
    }
}