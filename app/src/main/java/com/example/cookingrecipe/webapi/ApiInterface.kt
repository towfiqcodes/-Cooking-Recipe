package com.example.cookingrecipe.webapi

import com.example.cookingrecipe.mvp.login.model.LoginResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @Multipart
    @POST(WebMethod.LOGIN)
    fun login(@Part("email") image: RequestBody?, @Part("password") memberId: RequestBody?): Observable<LoginResponse?>?
}