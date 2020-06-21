package com.example.cookingrecipe.webapi

import com.example.cookingrecipe.mvp.login.model.LoginResponse
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.model.recipesDetails.RecipesDetailsResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    @Multipart
    @POST(WebMethod.LOGIN)
    fun login(
        @Part("email") image: RequestBody?,
        @Part("password") memberId: RequestBody?
    ): Observable<LoginResponse?>?



    @GET(WebMethod.GET_ALL_RECIPES)
    fun getAllRecipes(
        @Header("Authorization") authorization: String?
    ): Observable<AllRecipeResponse?>?



    @GET(WebMethod.GET_RECIPES_DETAILS)
    fun getRecipeDetails(
        @Header("Authorization") authorization: String?
    ): Observable<RecipesDetailsResponse?>?

}