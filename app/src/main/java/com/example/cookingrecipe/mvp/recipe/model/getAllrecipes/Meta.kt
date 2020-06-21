package com.example.cookingrecipe.mvp.recipe.model.getAllrecipes

data class Meta(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val path: String,
    val per_page: Int,
    val to: Int,
    val total: Int
)