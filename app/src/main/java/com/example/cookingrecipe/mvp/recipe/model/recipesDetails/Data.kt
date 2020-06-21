package com.example.cookingrecipe.mvp.recipe.model.recipesDetails

data class Data(
    val cook_time: Any,
    val created_at: Any,
    val directions: List<Direction>,
    val id: String,
    val image_url: String,
    val ingredients: List<Ingredient>,
    val notes: String,
    val prep_time: Any,
    val servings: Int,
    val source: Source,
    val source_url: String,
    val title: String,
    val total_time: Int
)