package com.example.cookingrecipe.mvp.recipe.presenter

import android.content.Context
import com.example.cookingrecipe.mvp.login.model.LoginResponse
import com.example.cookingrecipe.mvp.recipe.controller.AllRecipeController
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.view.AllrecipeView
import com.example.cookingrecipe.webapi.ApiInterface
import com.example.cookingrecipe.webapi.ConnectionURL
import com.example.cookingrecipe.webapi.ServiceFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AllRecipePresenter(val allrecipeView: AllrecipeView, val context: Context):AllRecipeController {

    override fun getAllRecipeResponse(token: String) {
     setAllRecipe(token)?.subscribeWith(getDisposal())
    }

    override fun setAllRecipe(token: String): Observable<AllRecipeResponse?>? {
       return ServiceFactory.createService(context,ApiInterface::class.java,ConnectionURL.BASE_URL)
           .getAllRecipes(token)
           ?.subscribeOn(Schedulers.io())
           ?.observeOn(AndroidSchedulers.mainThread())
    }


    fun getDisposal(): DisposableObserver<AllRecipeResponse?>? {
        return object : DisposableObserver<AllRecipeResponse?>() {
            override fun onNext(allRecipeResponse: AllRecipeResponse) {
                allrecipeView.displayGetAllRecipe(allRecipeResponse)
                allrecipeView.hideProgressBar()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                allrecipeView.displayError(e.message)
                allrecipeView.displayExpception(e)
                allrecipeView.hideProgressBar()
            }

            override fun onComplete() {
                allrecipeView.hideProgressBar()
            }
        }
    }
}