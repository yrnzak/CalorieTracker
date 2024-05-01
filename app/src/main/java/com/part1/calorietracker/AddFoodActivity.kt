package com.part1.calorietracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddFoodActivity : AppCompatActivity() {

    private lateinit var foodNameEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var addFoodItemButton: Button

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var foodAdapter: FoodItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        // Initialize views
        foodNameEditText = findViewById(R.id.foodNameEditText)
        caloriesEditText = findViewById(R.id.caloriesEditText)
        addFoodItemButton = findViewById(R.id.addFoodItemButton)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("FoodPrefs", MODE_PRIVATE)

        // Initialize FoodItemAdapter with an empty list
        foodAdapter = FoodItemAdapter(this, mutableListOf())

        // Set click listener for the add food button
        addFoodItemButton.setOnClickListener {
            // Get the text entered in the EditText fields
            val foodName = foodNameEditText.text.toString()
            val caloriesText = caloriesEditText.text.toString()

            // Validate input
            if (foodName.isEmpty()) {
                foodNameEditText.error = "Please enter food name"
                return@setOnClickListener
            }
            val calories = caloriesText.toIntOrNull()
            if (calories == null) {
                caloriesEditText.error = "Please enter valid calories"
                return@setOnClickListener
            }

            // Check if food name already exists
            val foodNames = sharedPreferences.getStringSet("foodNames", mutableSetOf()) ?: mutableSetOf()
            if (foodNames.contains(foodName)) {
                foodNameEditText.error = "Food name already exists"
                return@setOnClickListener
            }

            // Save the new FoodItem to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putStringSet("foodNames", foodNames.apply { add(foodName) })
            editor.putString(foodName, "$foodName,$calories")
            editor.apply()

            // Add the new food item to the adapter
            foodAdapter.addItem(Food(foodName, calories))

            // Notify the adapter that the data set has changed
            foodAdapter.notifyDataSetChanged()

            finish()
        }
    }
}
