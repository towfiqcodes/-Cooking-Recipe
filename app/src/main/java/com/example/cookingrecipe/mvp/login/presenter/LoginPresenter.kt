package com.example.cookingrecipe.mvp.login.presenter

import android.content.Context
import com.example.cookingrecipe.mvp.login.controller.Logincontroller
import com.example.cookingrecipe.mvp.login.model.LoginResponse
import com.example.cookingrecipe.mvp.login.view.LoginView
import com.example.cookingrecipe.webapi.ApiInterface
import com.example.cookingrecipe.webapi.ConnectionURL
import com.example.cookingrecipe.webapi.ServiceFactory
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class LoginPresenter(val loginView: LoginView, val context: Context): Logincontroller {


    override fun getLoginConnection(email: RequestBody, password: RequestBody) {
       getLoginResponse(email,password)?.subscribeWith(getDisposal())
    }



    override fun getLoginResponse(
        email: RequestBody,
        password: RequestBody
    ): Observable<LoginResponse?>? {
        return ServiceFactory.createService(context,ApiInterface::class.java,ConnectionURL.BASE_URL)
            .login(email,password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }



    fun getDisposal(): DisposableObserver<LoginResponse?>? {
        return object : DisposableObserver<LoginResponse?>() {
            override fun onNext(loginResponse: LoginResponse) {
                loginView.displayLoginResponse(loginResponse)
                loginView.hideProgressBar()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                loginView.displayError(e.message)
                loginView.displayExpception(e)
                loginView.hideProgressBar()
            }

            override fun onComplete() {
                loginView.hideProgressBar()
            }
        }
    }
}