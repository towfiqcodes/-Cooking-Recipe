package com.example.cookingrecipe.mvp.login.view

import com.example.cookingrecipe.mvp.login.model.LoginResponse

interface LoginView {

    fun showToast(s: String?)

    fun showProgressBar()

    fun hideProgressBar()


    fun displayLoginResponse(loginResponse: LoginResponse?)


    fun displayError(s: String?)


    fun displayExpception(e: Throwable?)


    fun loginFunc(): Boolean


    fun EmailValidationFunc(strEmail: String): Boolean
}