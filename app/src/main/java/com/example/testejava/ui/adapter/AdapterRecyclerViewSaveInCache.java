package com.example.testejava.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testejava.R;
import com.example.testejava.model.Car;

import java.util.List;

public class AdapterRecyclerViewSaveInCache extends RecyclerView.Adapter<AdapterRecyclerViewSaveInCache.ViewHolder> {
    private com.example.testejava.ui.onMenuClick onMenuClick;

    private static List<Car> mListCars;
    private OnItemClickListener listener;

    public AdapterRecyclerViewSaveInCache(List<Car> data, OnItemClickListener listener) {
        this.mListCars = data;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_itens_salvos, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = mListCars.get(position);
        holder.onBindView(car, listener);
    }

    public void setCarros(List<Car> carros) {
        this.mListCars = carros;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListCars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewModel;
        TextView textViewPrice;
        TextView textViewYear;

        ImageView deleteCar;
        ImageView saveArchive;
        Car car;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewModel = itemView.findViewById(R.id.txtModeloSaves);
            textViewPrice = itemView.findViewById(R.id.txtValorSaves);
            textViewYear = itemView.findViewById(R.id.txtAnoSaves);
            deleteCar = itemView.findViewById(R.id.deleteCar);
            saveArchive = itemView.findViewById(R.id.saveArchive);
        }

        public void onBindView(Car car, OnItemClickListener listener) {
            this.car = car;
            textViewModel.setText(car.getModel());
            textViewPrice.setText(car.getPrice());
            textViewYear.setText(car.getYear());

            saveArchive.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSaveItemClick(position);
                }

            });
            deleteCar.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteCar(position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onDeleteCar(int position);

        void onSaveItemClick(int position);
    }
}
