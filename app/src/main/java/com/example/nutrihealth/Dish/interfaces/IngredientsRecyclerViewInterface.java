package com.example.nutrihealth.Dish.interfaces;


import com.example.nutrihealth.Dish.models.ingredient.Ingredient;


/**
 * Interface for handling IngredientRecyclerView user interaction for add/edit and delete ingredient
 */
public interface IngredientsRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Ingredient ing, int position);
}
