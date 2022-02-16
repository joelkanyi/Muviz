package com.kanyideveloper.muviz.domain.model

import androidx.annotation.DrawableRes
import com.kanyideveloper.muviz.R

// I have prepared the following data structures and resources to skip the boring part

data class Recipe(
    val title: String,
    val category: String,
    val cookingTime: String,
    val energy: String,
    val rating: String,
    val description: String,
    val reviews: String,
    val ingredients: List<Ingredient>
)

data class Ingredient(@DrawableRes val image: Int, val title: String, val subtitle: String)

val strawberryCake = Recipe(
    title = "Strawberry Cake",
    category = "Desserts",
    cookingTime = "50 min",
    energy = "620 kcal",
    rating = "4,9",
    description = "This dessert is very tasty and not difficult to prepare. Also, you can replace strawberries with any other berry you like.",
    reviews = "84 photos     430 comments",
    ingredients = listOf(
        Ingredient(R.drawable.motherland, "Flour", "450 g"),
        Ingredient(R.drawable.motherland, "Eggs", "4"),
        Ingredient(R.drawable.motherland, "Lemon juice", "150 g"),
        Ingredient(R.drawable.motherland, "Strawberry", "200 g"),
        Ingredient(R.drawable.motherland, "Sugar", "1 cup"),
        Ingredient(R.drawable.motherland, "Mind", "20 g"),
        Ingredient(R.drawable.motherland, "Vanilla", "1/2 teaspoon"),
    )
)