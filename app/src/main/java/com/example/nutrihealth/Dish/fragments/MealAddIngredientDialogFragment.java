package com.example.nutrihealth.Dish.fragments;



import static com.example.nutrihealth.Dish.fragments.MealAddEditDialogFragment.selectedIngredientsToAddToMeal;

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

import com.example.nutrihealth.Dish.adapters.MealsAddIngredientListViewAdapter;
import com.example.nutrihealth.Dish.adapters.MealsSelectedNewIngredientsListViewAdapter;
import com.example.nutrihealth.Dish.dbHelpers.IngredientDBHelper;
import com.example.nutrihealth.Dish.models.ingredient.Ingredient;
import com.example.nutrihealth.R;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealAddIngredientDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealAddIngredientDialogFragment extends DialogFragment {

    static MealsAddIngredientListViewAdapter mealsAddIngredientListViewAdapter;
    Button btnCancel;
    Button btnAddSelectedIngredientsToMeal;
    ListView listViewAddIngredientsToMeal;

    static MealsSelectedNewIngredientsListViewAdapter selectedMealAdapter;

    /**
     * Required empty constructor
     */
    public MealAddIngredientDialogFragment() {}

    public static MealAddIngredientDialogFragment newInstance(MealsSelectedNewIngredientsListViewAdapter adapter) {
        MealAddIngredientDialogFragment fragment = new MealAddIngredientDialogFragment();
        selectedMealAdapter = adapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_add_ingredient_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCancel = view.findViewById(R.id.btn_meal_add_ingredient_cancel);
        btnAddSelectedIngredientsToMeal = view.findViewById(R.id.btn_meal_add_ingredient_add_selected_items);
        listViewAddIngredientsToMeal = view.findViewById(R.id.lv_ingredients_available_to_add_to_meal);
        ArrayList<Ingredient> ingredientArrayList = IngredientDBHelper.getIngredientsFromStorage();

        mealsAddIngredientListViewAdapter = new MealsAddIngredientListViewAdapter(getContext(), ingredientArrayList);
        listViewAddIngredientsToMeal.setAdapter(mealsAddIngredientListViewAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        btnAddSelectedIngredientsToMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loop through all ingredients. Add whatever is checked to the array of ing to be added
                for(Ingredient ingredient : ingredientArrayList){
                    if(ingredient.isChecked()){
                        selectedIngredientsToAddToMeal.add(ingredient);
                        selectedMealAdapter.notifyDataSetChanged();
                    }
                    ingredient.setChecked(false); //ensure afterwards that all ingredients are set back to unchecked
                }
                dismiss();

            }
        });
    }
}