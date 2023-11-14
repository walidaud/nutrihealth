package com.example.nutrihealth.Dish.callbacks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihealth.Dish.adapters.IngredientsRecyclerViewAdapter;


/**
 * A simple {@link ItemTouchHelper} subclass.
 * Create an instance of the item touch helper
 * This function will check if there was a swipe in the recycler view and will handle it by calling the fakeDeleteForUndo function
 */
public class SwipeToDeleteIngredientCallback extends ItemTouchHelper.Callback {

    private IngredientsRecyclerViewAdapter mAdapter;

    /**
     * @param adapter of type recipesrecyclerviewadapter
     */
    public SwipeToDeleteIngredientCallback(IngredientsRecyclerViewAdapter adapter){mAdapter = adapter;}

    /**
     * Gets the movement flags needed to run the itemtouchhelper
     * @param recyclerView of type recycler view
     * @param viewHolder of type viewholder
     * @return the movement flag
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    /**
     * @param recyclerView of type recycler view
     * @param viewHolder of type viewholder
     * @param target of type viewholder
     * @return boolean
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Will call the fakeDeleteForUndo function from the adapter
     * @param viewHolder of type viewholder
     * @param direction of type int
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.fakeDeleteForUndo(position);
    }

}
