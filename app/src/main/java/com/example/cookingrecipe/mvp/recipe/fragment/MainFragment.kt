package com.example.cookingrecipe.mvp.recipe.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.cookingrecipe.R
import com.example.cookingrecipe.activity.MainActivity
import com.example.cookingrecipe.mvp.login.LoginActivity
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.presenter.AllRecipePresenter
import com.example.cookingrecipe.mvp.recipe.view.AllrecipeView
import com.example.cookingrecipe.utility.InternetConnectionManager
import com.example.cookingrecipe.utility.SharedPrefsHandler
import com.example.cookingrecipe.utility.dialog.DialogBox
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), AllrecipeView {
    val TAG: String = MainFragment::class.java.getSimpleName()
    var ll_click: LinearLayout? = null
    var ll_main_fragment: LinearLayout? = null
    var imageView: CircleImageView? = null
    var imageView4: CircleImageView? = null
    var imageView5: CircleImageView? = null
    var animation: Animation? = null
    var ac: MainActivity? = null
    var recipe_name: TextView? = null
    var recipe_title: TextView? = null
    var recipe_time: TextView? = null
    var tv_source: TextView? = null
    var pb_progress: ProgressBar? = null
    var image1: ImageView?=null
    var image2: ImageView?=null
    var image3: ImageView?=null


    var allRecipePresenter: AllRecipePresenter? = null
    var sharedPrefsHandler: SharedPrefsHandler? = null
    var dialogBox: DialogBox? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        globalSet()


        ll_click!!.setOnClickListener() {
            val breakfastFragment = BreakfastFragment()
            showFragment(breakfastFragment)
            ac!!.getButtonClick()
        }


        if (sharedPrefsHandler!!.getUserToken()!=null){
            allRecipePresenter?.getAllRecipeResponse("Bearer ${sharedPrefsHandler!!.getUserToken()}" )
        }


    }


    fun globalSet() {
        ac = activity as MainActivity?
        ac?.hideMenu()

        imageView = view?.findViewById(R.id.transition)
        imageView4 = view?.findViewById(R.id.imageView4)
        imageView5 = view?.findViewById(R.id.imageView5)
        image1= view?.findViewById<ImageView>(R.id.imageView1)
        image2= view?.findViewById<ImageView>(R.id.imageView2)
        image3= view?.findViewById<ImageView>(R.id.imageView3)

        ll_click = view?.findViewById(R.id.ll_click)
        ll_main_fragment = view?.findViewById(R.id.ll_main_fragment)
        recipe_name = view?.findViewById(R.id.recipe_name)
        recipe_title = view?.findViewById(R.id.recipe_title)
        recipe_time = view?.findViewById(R.id.recipe_time)
        tv_source = view?.findViewById(R.id.tv_source)
        pb_progress = view!!.findViewById(R.id.pb_progress)


        setCornerRoundedImageView()
        sharedPrefsHandler = SharedPrefsHandler(activity)
        dialogBox = DialogBox(activity!!)
        allRecipePresenter = AllRecipePresenter(this, activity!!)

    }


    fun showFragment(fragment: Fragment?) {
        fragmentManager!!.beginTransaction()
            /* fragmentTransaction*/
            .addSharedElement(imageView!!, ViewCompat.getTransitionName(imageView!!)!!)
            .addSharedElement(ll_click!!, ViewCompat.getTransitionName(ll_click!!)!!)
            .replace(R.id.activity_main_container, fragment!!) //comment for temp
            .addToBackStack(null)
            .commit()
    }


    companion object {

        fun newInstance(): MainFragment? {
            return MainFragment()
        }
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
                           logout()
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

    override fun showToast(s: String?) {
        if (s != null) {
            dialogBox!!.getDialog(s)
        }
    }

    override fun showProgressBar() {
        pb_progress!!.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb_progress!!.visibility = View.GONE
    }

    override fun displayGetAllRecipe(allRecipeResponse: AllRecipeResponse) {
        if (InternetConnectionManager.isConnectedToInternet(context!!)){
            if (allRecipeResponse!=null){
                hideProgressBar()
                ll_main_fragment?.visibility=View.VISIBLE
                recipe_name?.text = allRecipeResponse.data.get(0).id
                recipe_title?.text = allRecipeResponse.data.get(0).title
                recipe_time?.text = (allRecipeResponse.data.get(0).total_time).toString()+" cl"
                tv_source?.text = allRecipeResponse.data.get(0).source.name

                if (allRecipeResponse.data.get(0).source.image_url!=null){
                   /* Picasso.get().load(allRecipeResponse.data.get(0).source.image_url).into(imageView)
                    Picasso.get().load(allRecipeResponse.data.get(0).source.image_url).into(imageView4)
                    Picasso.get().load(allRecipeResponse.data.get(0).source.image_url).into(imageView5)*/
                }
            }

        }

    }

    override fun displayError(s: String?) {
        showToast(s)
        ll_main_fragment?.visibility=View.VISIBLE
    }

    override fun displayExpception(e: Throwable?) {
        onHandleErrorResponse(e)
        ll_main_fragment?.visibility=View.VISIBLE
    }

    private fun logout() {
        sharedPrefsHandler?.clear()
        try {
            Thread.sleep(16)
        } catch (x: Exception) {
            //do nothing
        }
        val loginIntent = Intent(context, LoginActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(loginIntent)
        activity!!.finish()
    }
    fun setCornerRoundedImageView(){
        val mbitmap1 =
            (resources.getDrawable(R.drawable.imge) as BitmapDrawable).bitmap
        val imageRounded =
            Bitmap.createBitmap(mbitmap1.width, mbitmap1.height, mbitmap1.config)
        val canvas = Canvas(imageRounded)
        val mpaint = Paint()
        mpaint.setAntiAlias(true)
        mpaint.setShader(BitmapShader(mbitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        canvas.drawRoundRect(
            RectF(0F, 0F, mbitmap1.width.toFloat(), mbitmap1.height.toFloat()),
            100F,
            100F,
            mpaint
        ) // Round Image Corner 100 100 100 100
        image1?.setImageBitmap(imageRounded)


        val mbitmap2 =
            (resources.getDrawable(R.drawable.image3) as BitmapDrawable).bitmap
        val imageRounded1 =
            Bitmap.createBitmap(mbitmap2.width, mbitmap2.height, mbitmap2.config)
        val canvas1 = Canvas(imageRounded1)
        val mpaint1 = Paint()
        mpaint1.setAntiAlias(true)
        mpaint1.setShader(BitmapShader(mbitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        canvas1.drawRoundRect(
            RectF(0F, 0F, mbitmap2.width.toFloat(), mbitmap2.height.toFloat()),
            100F,
            100F,
            mpaint1
        ) // Round Image Corner 100 100 100 100

        image2?.setImageBitmap(imageRounded1)

        val mbitmap3 =
            (resources.getDrawable(R.drawable.image4) as BitmapDrawable).bitmap
        val imageRounded2 =
            Bitmap.createBitmap(mbitmap3.width, mbitmap3.height, mbitmap2.config)
        val canvas2= Canvas(imageRounded2)
        val mpaint2 = Paint()
        mpaint2.setAntiAlias(true)
        mpaint2.setShader(BitmapShader(mbitmap3, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        canvas2.drawRoundRect(
            RectF(0F, 0F, mbitmap3.width.toFloat(), mbitmap3.height.toFloat()),
            100F,
            100F,
            mpaint2
        ) // Round Image Corner 100 100 100 100

        image3?.setImageBitmap(imageRounded2)
    }
}
