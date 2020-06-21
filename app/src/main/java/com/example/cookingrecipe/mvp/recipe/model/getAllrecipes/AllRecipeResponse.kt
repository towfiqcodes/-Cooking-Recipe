package com.example.cookingrecipe.mvp.recipe.model.getAllrecipes

data class AllRecipeResponse(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)