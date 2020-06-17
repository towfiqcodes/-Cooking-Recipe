package com.example.cookingrecipe.mvp.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.agrawalsuneet.loaderspack.loaders.CircularSticksLoader
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.login.model.LoginResponse
import com.example.cookingrecipe.mvp.login.presenter.LoginPresenter
import com.example.cookingrecipe.mvp.login.view.LoginView
import com.example.cookingrecipe.mvp.recipe.RecipeActivity
import com.example.cookingrecipe.utility.InternetConnectionManager
import com.example.cookingrecipe.utility.SharedPrefsHandler
import com.example.cookingrecipe.utility.dialog.DialogBox

import com.google.android.material.textfield.TextInputEditText
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginView {
    var activity: LoginActivity? = null

    var emailEt: TextInputEditText? = null
    var passwordEt: TextInputEditText? = null
    var btnNext: Button? = null
    var tv_user: TextView? = null
    var userButton: String? = null

    var sharedPrefsHandler: SharedPrefsHandler? = null
    var dialogBox: DialogBox? = null
    var loginPresenter: LoginPresenter? = null

    var loader: CircularSticksLoader?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activity = this

        Logger.addLogAdapter(AndroidLogAdapter())
        sharedPrefsHandler = SharedPrefsHandler(activity)
        dialogBox = DialogBox(activity!!)
        loginPresenter = LoginPresenter(this)


        loader = findViewById(R.id.progress)
        emailEt = findViewById(R.id.emailEt)
        passwordEt = findViewById(R.id.passwordEt)
        btnNext = findViewById(R.id.btnNext)
        btnNext!!.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnNext -> {
                if (loginFunc()) {
                    sendToServer()
                } else {
                 //   showToast("invalid_email_or_password")
                    //   Toast.makeText(this@LoginActivity,"Email Or Password not valid", Toast.LENGTH_LONG).show()
                }


            }
        }

    }

    override fun showToast(s: String?) {
        if (s != null) {
            dialogBox!!.getDialog(s)
        }
    }

    override fun showProgressBar() {
        loader!!.setVisibility(View.VISIBLE)

    }

    override fun hideProgressBar() {
        loader!!.setVisibility(View.GONE)
    }

    override fun displayLoginResponse(loginResponse: LoginResponse?) {

        if (InternetConnectionManager.isConnectedToInternet(this)) {
            if (loginResponse!!.response.equals("success")) {
                hideProgressBar()
                val intent = Intent(activity, RecipeActivity::class.java)
                startActivity(intent)
                sharedPrefsHandler!!.setUserToken(loginResponse!!.result.token)
            } else {
                hideProgressBar()
            showToast("Invalid email & password")
            }

        } else {
            hideProgressBar()
            showToast("Please check your internet connection")
        }

    }

    override fun displayError(s: String?) {


    }

    override fun displayExpception(e: Throwable?) {
        onHandleErrorResponse(e)
    }


    override fun loginFunc(): Boolean {
        val strEmail = emailEt!!.text.toString().trim()
        val strPassword = passwordEt!!.text.toString().trim()

        if (TextUtils.isEmpty(strEmail)) {
            emailEt?.error = "enter the Email!!"

        } else if (TextUtils.isEmpty(strPassword)) {
            passwordEt?.error = "enter the Password!!"
        } else if (!EmailValidationFunc(strEmail)) {
            emailEt?.error = "Not VAlid Email Address"

        } else {
            return true
        }
        return false
    }

    override fun EmailValidationFunc(strEmail: String): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
        val match: Matcher = pattern.matcher(strEmail)
        return match.matches()
    }


    fun onHandleErrorResponse(throwable: Throwable?): Boolean {
        var isValid = false
        if (throwable is HttpException) {
            // We had non-2XX http error
            val responseBody = throwable.response()!!.errorBody()
            if (responseBody != null) {
                var jObjError: JSONObject? = null
                try {
                    jObjError = JSONObject(responseBody.string())
                    if (jObjError != null) {
                        if (jObjError.getInt("token_error_code") == 420) {
                            showToast("Token Code Error")
                            isValid = true
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                //Log.e("TripHistory","Error-profile jObjError :"+jObjError.toString());
            }

            //Log.e("TripHistory","Error-profile HttpException :"+((HttpException) throwable).response().code());
        }
        return isValid
    }


    fun sendToServer() {
        val strEmail = emailEt!!.text.toString().trim()
        val strPassword = passwordEt!!.text.toString().trim()

        val requestEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail)
        val requestPassword =
            RequestBody.create(MediaType.parse("multipart/form-data"), strPassword)

        loginPresenter!!.getLoginConnection(requestEmail, requestPassword)
        showProgressBar()
    }




}