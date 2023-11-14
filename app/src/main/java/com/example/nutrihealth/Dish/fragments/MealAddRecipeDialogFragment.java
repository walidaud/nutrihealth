package com.example.nutrihealth.Dish.fragments;



import static com.example.nutrihealth.Dish.fragments.MealAddEditDialogFragment.selectedRecipesToAddToMeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.example.nutrihealth.Dish.adapters.MealsAddRecipeListViewAdapter;
import com.example.nutrihealth.Dish.adapters.MealsSelectedNewRecipesListViewAdapter;
import com.example.nutrihealth.Dish.dbHelpers.RecipeDBHelper;
import com.example.nutrihealth.Dish.models.recipe.Recipe;
import com.example.nutrihealth.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealAddRecipeDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealAddRecipeDialogFragment extends DialogFragment {

    MealsAddRecipeListViewAdapter mealsAddRecipeListViewAdapter;
    private Button btnCancel;
    private Button btnAddSelectedRecipesToMeal;
    ListView listViewAddRecipesToMeal;

    static MealsSelectedNewRecipesListViewAdapter mealsSelectedNewRecipesListViewAdapter;


    /**
     * Mandatory empty constructor
     */
    public MealAddRecipeDialogFragment() {
        // Required empty public constructor
    }

    public static MealAddRecipeDialogFragment newInstance(MealsSelectedNewRecipesListViewAdapter adapter) {
        MealAddRecipeDialogFragment fragment = new MealAddRecipeDialogFragment();
        mealsSelectedNewRecipesListViewAdapter = adapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_add_recipe_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCancel = view.findViewById(R.id.btn_meal_add_recipe_cancel);
        btnAddSelectedRecipesToMeal = view.findViewById(R.id.btn_meal_add_recipe_add_selected_items);
        listViewAddRecipesToMeal = view.findViewById(R.id.lv_recipes_available_to_add_to_meal);
        ArrayList<Recipe> recipeArrayList = RecipeDBHelper.getRecipesFromStorage();

        mealsAddRecipeListViewAdapter = new MealsAddRecipeListViewAdapter(getContext(), recipeArrayList);
        listViewAddRecipesToMeal.setAdapter(mealsAddRecipeListViewAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        btnAddSelectedRecipesToMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loop through all recipes. Add whatever is checked to the array of ing to be added
                for(Recipe recipe : recipeArrayList){
                    if(recipe.isChecked()){
                        selectedRecipesToAddToMeal.add(recipe);
                        mealsSelectedNewRecipesListViewAdapter.notifyDataSetChanged();
                    }
                    recipe.setChecked(false); //ensure afterwards that all recipes are set back to unchecked
                }
                dismiss();
            }
        });
    }
}