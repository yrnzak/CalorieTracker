package com.part1.calorietracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FoodItemAdapter(context: Context, private val foodItems: MutableList<Food>) :
    ArrayAdapter<Food>(context, 0, foodItems) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.list_item_food, parent, false)
        }

        val foodItem = foodItems[position]

        val foodNameTextView: TextView = itemView!!.findViewById(R.id.foodNameTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)

        foodNameTextView.text = foodItem.name
        caloriesTextView.text = "${foodItem.calories}"

        return itemView
    }

    fun addItem(item: Food) {
        foodItems.add(item)
        notifyDataSetChanged()
    }
}
