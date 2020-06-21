package com.example.cookingrecipe.mvp.recipe.model.getAllrecipes

data class Ingredient(
    val description: Any,
    val display_quantity: String,
    val id: Int,
    val name: String,
    val preparation: String,
    val quantity: String,
    val unit: Unit
)