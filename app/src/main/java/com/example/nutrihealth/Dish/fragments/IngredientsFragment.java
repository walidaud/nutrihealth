package com.example.nutrihealth.Dish.fragments;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nutrihealth.Dish.adapters.IngredientsRecyclerViewAdapter;
import com.example.nutrihealth.Dish.callbacks.SwipeToDeleteIngredientCallback;
import com.example.nutrihealth.Dish.dbHelpers.IngredientDBHelper;
import com.example.nutrihealth.Dish.interfaces.IngredientsRecyclerViewInterface;
import com.example.nutrihealth.Dish.models.ingredient.Ingredient;
import com.example.nutrihealth.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//TODO - ON START, CHECK IF ANY INGREDIENTS ARE PAST DUE DATE. IF THEY ARE , SET COUNT TO ZERO AND HIGHLIGH
//  ITEM IN INGREDIENT LIST RED WITH TOAST MESSAGE.

public class IngredientsFragment extends Fragment implements IngredientsRecyclerViewInterface, MenuProvider{

    RecyclerView rvIngredients;
    FloatingActionButton fabAddIngredient;
    IngredientsRecyclerViewAdapter rvAdapter;
    static ProgressBar progressBar;


    /**
     * Required empty constructor
     */
    public IngredientsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ingredients List");

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){

                    case R.id.action_sort_by_name:
                        rvAdapter.sortIngredientByName();
                        break;

                    case R.id.action_sort_by_description:
                        rvAdapter.sortIngredientByDescription();
                        break;

                    case R.id.action_sort_by_best_before_date:
                        rvAdapter.sortIngredientByBestBeforeDate();
                        break;

                    case R.id.action_sort_by_location:
                        rvAdapter.sortIngredientByLocation();
                        break;

                    case R.id.action_sort_by_amount:
                        rvAdapter.sortIngredientByAmount();
                        break;

                    case R.id.action_sort_by_unit:
                        rvAdapter.sortIngredientByUnit();
                        break;

                    case R.id.action_sort_by_category:
                        rvAdapter.sortIngredientByCategory();
                        break;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ingredient, container, false);

        rvIngredients = (RecyclerView) root.findViewById(R.id.rv_ingredients_list);
        fabAddIngredient = root.findViewById(R.id.fab_ingredient_add);
        progressBar = root.findViewById(R.id.progressBarId);

        setupRecyclerView();

        fabAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        //TODO - Fix this . Figure out how to pass the adapter to the db helper without using a constructor
        //Creates an instance of DB helper to initiate event listener and pass reference of RV adapter
//        IngredientDBHelper dbHelper = new IngredientDBHelper(rvAdapter);

        IngredientDBHelper.setupSnapshotListenerForIngredientRVAdapter(rvAdapter);

        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.splash_image);
        root.setBackground(d);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
//        setExpiredIngredientsAmountToZero();
    }

    private void setupRecyclerView(){
        rvAdapter = new IngredientsRecyclerViewAdapter(this);
        rvIngredients.setAdapter(rvAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteIngredientCallback(rvAdapter));
        itemTouchHelper.attachToRecyclerView(rvIngredients);
    }

    private void showEditDialog(Ingredient selectedIngredient) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        selectedIngredient, rvAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }

    private void showAddDialog() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        IngredientAddEditDialogFragment editNameDialogFragment =
                IngredientAddEditDialogFragment.newInstance(
                        rvAdapter);
        editNameDialogFragment.show(fm, "fragment_ingredient_add_edit_dialog");
    }

    @Override
    public void onItemLongClick(int position) {
        Ingredient selectedIngredient = rvAdapter.getItem(position);
        showEditDialog(selectedIngredient);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.ingredient_sort_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemDeleted(Ingredient ing, int position) {
            Snackbar snackbar = Snackbar.make(this.getView(), "Are You sure you want to delete this?",
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("DELETE", v -> IngredientDBHelper.deleteIngredientFromDB(ing, position));
            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        // IF Snackbar closed on its own, item was NOT deleted
                        rvAdapter.notifyDataSetChanged();
                    }
                }
            });
            snackbar.show();
    }

    static public void stopIngredientsFragmentProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}