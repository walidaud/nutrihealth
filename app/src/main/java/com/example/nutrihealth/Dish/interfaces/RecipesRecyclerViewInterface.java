package com.example.nutrihealth.Dish.interfaces;

//Respond to user clicks on items in recipe reyclerView

import com.example.nutrihealth.Dish.models.recipe.Recipe;


/**
 * Interface for handling RecipesRecyclerView user interaction for add/edit and delete recipe
 */
public interface RecipesRecyclerViewInterface {
    void onItemLongClick(int position);
    void onItemDeleted(Recipe recipe, int position);

}
