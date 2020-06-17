package com.example.cookingrecipe.mvp.login.controller

import com.example.cookingrecipe.mvp.login.model.LoginResponse
import io.reactivex.Observable
import okhttp3.RequestBody

interface Logincontroller {
    fun getLoginResponse(email:RequestBody, password:RequestBody):Observable<LoginResponse?>?
    fun getLoginConnection(email:RequestBody, password:RequestBody)
}