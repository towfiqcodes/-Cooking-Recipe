package com.example.cookingrecipe.mvp.recipe.view

import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse

interface AllrecipeView {

    fun showToast(s: String?)

    fun showProgressBar()

    fun hideProgressBar()


    fun displayGetAllRecipe(allRecipeResponse: AllRecipeResponse)


    fun displayError(s: String?)


    fun displayExpception(e: Throwable?)

}