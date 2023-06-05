package com.example.testejava.ui.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testejava.R;
import com.example.testejava.model.Car;
import com.example.testejava.ui.onMenuClick;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    private static List<Car> mListCars;
    private com.example.testejava.ui.onMenuClick onMenuClick;

    public AdapterRecyclerView(onMenuClick onMenuClick, List<Car> data) {
        this.onMenuClick = onMenuClick;
        this.mListCars = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista, parent, false);
        return new ViewHolder(view, onMenuClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindView(mListCars.get(position));
    }

    @Override
    public int getItemCount() {
        return mListCars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
        TextView textViewModel;
        TextView textViewPrice;
        TextView textViewYear;
        TextView saveId;
        ImageView menuItem;
        Car car;
        onMenuClick onMenuClick;

        public ViewHolder(@NonNull View itemView, onMenuClick onMenuClick) {
            super(itemView);
            this.onMenuClick = onMenuClick;
            menuItem = itemView.findViewById(R.id.menuItens);
            saveId = itemView.findViewById(R.id.saveId);
            textViewModel = itemView.findViewById(R.id.txtModelo);
            textViewPrice = itemView.findViewById(R.id.txtValor);
            textViewYear = itemView.findViewById(R.id.txtAno);
        }

        public void onBindView(Car car) {
            this.car = car;
            saveId.setText(car.get_id());
            textViewModel.setText(car.getModel());
            textViewPrice.setText(car.getPrice());
            textViewYear.setText(car.getYear());
            menuItem.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(itemView.getContext(), view);
                popupMenu.inflate(R.menu.recycler_item_menu);
                popupMenu.setOnMenuItemClickListener(ViewHolder.this);
                popupMenu.show();
            });
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuEdit:
                    onMenuClick.onEdit(this.car);
                    return true;

                case R.id.menuSave:
                    onMenuClick.onSaveinCache(this.car);
                    return true;

                case R.id.menuDelet:
                    onMenuClick.onDelete(this.car.get_id());
                    return true;
            }
            return false;
        }
    }
}
