package com.example.cookingrecipe.mvp.recipe.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.cookingrecipe.R
import com.example.cookingrecipe.activity.MainActivity
import com.example.cookingrecipe.mvp.recipe.adapter.IngredientsAdapter
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Ingredient
import com.example.cookingrecipe.mvp.recipe.presenter.AllRecipePresenter
import com.example.cookingrecipe.mvp.recipe.view.AllrecipeView
import com.example.cookingrecipe.utility.InternetConnectionManager
import com.example.cookingrecipe.utility.SharedPrefsHandler
import com.example.cookingrecipe.utility.dialog.DialogBox
import de.hdodenhof.circleimageview.CircleImageView


/**
 * A simple [Fragment] subclass.
 */
class BreakfastFragment : Fragment(),AllrecipeView {
    var transition:CircleImageView?=null

    var ac:MainActivity?=null

    var tv_source:TextView?=null
    var recipe_title:TextView?=null
    var tv_total_time:TextView?=null


    var allRecipePresenter: AllRecipePresenter? = null
    var sharedPrefsHandler: SharedPrefsHandler? = null
    var dialogBox: DialogBox? = null

    var rv_ingredient:RecyclerView?=null
    var ingredientsAdapter:IngredientsAdapter?=null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breakfast, container, false)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sharedElementEnterTransition =
                    TransitionInflater.from(context)
                        .inflateTransition(R.transition.fade_in_transition)
            }

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      globalSet()

        if (sharedPrefsHandler!!.getUserToken()!=null){
            allRecipePresenter?.getAllRecipeResponse("Bearer ${sharedPrefsHandler!!.getUserToken()}" )
        }
    }


    fun globalSet() {
        ac= activity as MainActivity?
        ac?.showMenu()
        tv_source=view!!.findViewById(R.id.tv_source)
        recipe_title=view!!.findViewById(R.id.recipe_title)
        tv_total_time=view!!.findViewById(R.id.tv_total_time)
        rv_ingredient=view!!.findViewById(R.id.rv_ingredient)

        sharedPrefsHandler = SharedPrefsHandler(activity)
        dialogBox = DialogBox(activity!!)
        allRecipePresenter = AllRecipePresenter(this, activity!!)
    }

    companion object{

    fun newInstance(): BreakfastFragment? {
        return BreakfastFragment()
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
        if (InternetConnectionManager.isConnectedToInternet(activity!!)){
            if (allRecipeResponse!=null){
                tv_source?.text = allRecipeResponse.data.get(0).source.name
                recipe_title?.text = allRecipeResponse.data.get(0).id
                tv_total_time?.text = (allRecipeResponse.data.get(0).total_time).toString() + " cl"
                setRecyclerView(allRecipeResponse.data.get(0).ingredients)
            }
        }

    }

    private fun setRecyclerView(ingredients: List<Ingredient>) {
        if(ingredients.size>0){
            rv_ingredient?.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            ingredientsAdapter = IngredientsAdapter(activity!!, ingredients)
            rv_ingredient?.adapter = ingredientsAdapter
        }

    }

    override fun displayError(s: String?) {
        showToast(s)
    }

    override fun displayExpception(e: Throwable?) {

    }


}

