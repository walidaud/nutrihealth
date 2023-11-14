package com.example.nutrihealth.Database;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihealth.R;

import java.util.ArrayList;

public class AdapterDish extends RecyclerView.Adapter<AdapterDish.ViewHolder> {


    // creating a variable for array list and context.
    private ArrayList<Dish> dishArrayList;

    // creating a constructor for our variables.
    public AdapterDish(ArrayList<Dish> dishArrayList, Context context) {
        this.dishArrayList = dishArrayList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Dish> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        dishArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterDish.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dishes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDish.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Dish model = dishArrayList.get(position);
        holder.dishNameTV.setText(model.getDishName());
        holder.dishDescTV.setText(model.getDishDescription());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return dishArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView dishNameTV;
        private final TextView dishDescTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            dishNameTV = itemView.findViewById(R.id.idTVCourseName);
            dishDescTV = itemView.findViewById(R.id.idTVCourseDescription);
        }
    }
}