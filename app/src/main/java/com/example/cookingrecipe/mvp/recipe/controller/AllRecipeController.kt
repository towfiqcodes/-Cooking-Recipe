package com.example.cookingrecipe.mvp.recipe.controller

import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import io.reactivex.Observable

interface AllRecipeController {
    fun getAllRecipeResponse(token: String)
    fun setAllRecipe(token: String): Observable<AllRecipeResponse?>?
}