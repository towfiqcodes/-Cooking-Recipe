package com.example.cookingrecipe.mvp.recipe.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.cookingrecipe.DataItemClickListener

import com.example.cookingrecipe.R
import com.example.cookingrecipe.activity.MainActivity
import com.example.cookingrecipe.mvp.recipe.adapter.DinnerAdapter
import com.example.cookingrecipe.mvp.recipe.adapter.IngredientsAdapter
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Data
import com.example.cookingrecipe.mvp.recipe.presenter.AllRecipePresenter
import com.example.cookingrecipe.mvp.recipe.view.AllrecipeView
import com.example.cookingrecipe.utility.InternetConnectionManager
import com.example.cookingrecipe.utility.SharedPrefsHandler
import com.example.cookingrecipe.utility.dialog.DialogBox
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_main.*


class DinnerFragment : Fragment(), AllrecipeView , DataItemClickListener{
    var ac:MainActivity?=null

    var allRecipePresenter: AllRecipePresenter? = null
    var sharedPrefsHandler: SharedPrefsHandler? = null
    var dialogBox: DialogBox? = null

    var rv_dinner: RecyclerView?=null
    var dinnerAdapter: DinnerAdapter?=null


    var transition:CircleImageView?=null
    var ll_clic:LinearLayout?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dinner, container, false)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.slide_left)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gobalData()

        if (sharedPrefsHandler!!.getUserToken()!=null){
            allRecipePresenter?.getAllRecipeResponse("Bearer ${sharedPrefsHandler!!.getUserToken()}" )
        }

    }



    fun gobalData(){
        ac= activity as MainActivity?
        ac!!.showMenu()

        transition=view?.findViewById(R.id.transition)
        ll_clic=view?.findViewById(R.id.ll_clic)
        rv_dinner=view?.findViewById(R.id.rv_dinner)

        sharedPrefsHandler = SharedPrefsHandler(activity)
        dialogBox = DialogBox(activity!!)
        allRecipePresenter = AllRecipePresenter(this, activity!!)

    }

    companion object {
        fun newInstance():DinnerFragment{
            return DinnerFragment()
        }
    }


    override fun showToast(s: String?) {
        if (s != null) {
            dialogBox?.getDialog(s)
        }
    }

    override fun showProgressBar() {
    }

    override fun hideProgressBar() {
    }

    override fun displayGetAllRecipe(allRecipeResponse: AllRecipeResponse) {
        if (InternetConnectionManager.isConnectedToInternet(context!!)){
            if (allRecipeResponse!=null){
                setRecyclerView(allRecipeResponse.data)
            }
        }
    }



    private fun setRecyclerView(data: List<Data>) {

        rv_dinner?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )


        dinnerAdapter = DinnerAdapter(activity!!, data!!,this)
        rv_dinner?.adapter = dinnerAdapter

    }

    override fun displayError(s: String?) {
        showToast(s)
    }

    override fun displayExpception(e: Throwable?) {
        showToast(e?.message)
    }

    override fun onDataClickListener(pos: Int, data: Data, imageView: CircleImageView, linearLayout: LinearLayout) {
        val breakfastFragment:BreakfastFragment= BreakfastFragment.newInstance()!!
        fragmentManager!!.beginTransaction()
            /* fragmentTransaction*/
            .addSharedElement(imageView!!, ViewCompat.getTransitionName(imageView!!)!!)
            .addSharedElement(linearLayout!!, ViewCompat.getTransitionName(linearLayout!!)!!)
            .replace(R.id.activity_main_container, breakfastFragment!!) //comment for temp
            .addToBackStack(null)
            .commit()

    }
}




