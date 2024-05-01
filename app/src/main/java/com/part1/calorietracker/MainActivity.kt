package com.part1.calorietracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var addFoodButton: Button
    private lateinit var foodAdapter: FoodItemAdapter
    private lateinit var noFoodTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        listView = findViewById(R.id.listView)
        noFoodTextView = findViewById(R.id.noFoodTextView)
        addFoodButton = findViewById(R.id.addFoodButton)


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("FoodPrefs", MODE_PRIVATE)

        // Initialize adapter
        foodAdapter = FoodItemAdapter(this, mutableListOf())
        listView.adapter = foodAdapter

        // Set click listener for the add food button
        addFoodButton.setOnClickListener {
            // Open activity to add new food
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload food items
        loadFoodItems()
    }


    private fun loadFoodItems() {
        val foodItems = mutableListOf<Food>()
        val foodNames = sharedPreferences.getStringSet("foodNames", setOf()) ?: setOf()
        if (foodNames.isEmpty()) { //check if the list is empty
            noFoodTextView.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            noFoodTextView.visibility = View.GONE
            listView.visibility = View.VISIBLE
            for (foodName in foodNames) {
                val foodEntry = sharedPreferences.getString(foodName, null)
                foodEntry?.split(",")?.let { entry ->
                    if (entry.size == 2) {
                        val calories = entry[1].toIntOrNull()
                        calories?.let {
                            foodItems.add(Food(foodName, it))

                        }
                    }
                }
            }
            // Update adapter with the new data
            foodAdapter.clear()
            foodAdapter.addAll(foodItems)
            foodAdapter.notifyDataSetChanged()
        }
    }
}
