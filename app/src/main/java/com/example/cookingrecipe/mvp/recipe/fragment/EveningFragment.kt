package com.example.cookingrecipe.mvp.recipe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.R
import com.example.cookingrecipe.activity.MainActivity
import com.example.cookingrecipe.mvp.recipe.adapter.DinnerAdapter
import com.example.cookingrecipe.mvp.recipe.adapter.DirectionAdapter
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.AllRecipeResponse
import com.example.cookingrecipe.mvp.recipe.model.getAllrecipes.Direction
import com.example.cookingrecipe.mvp.recipe.presenter.AllRecipePresenter
import com.example.cookingrecipe.mvp.recipe.view.AllrecipeView
import com.example.cookingrecipe.utility.InternetConnectionManager
import com.example.cookingrecipe.utility.SharedPrefsHandler
import com.example.cookingrecipe.utility.dialog.DialogBox


/**
 * A simple [Fragment] subclass.
 */
class EveningFragment : Fragment(), AllrecipeView {
    val TAG: String = EveningFragment::class.java.getSimpleName()
    var ac: MainActivity? = null

    var allRecipePresenter: AllRecipePresenter? = null
    var sharedPrefsHandler: SharedPrefsHandler? = null
    var dialogBox: DialogBox? = null
    var pb_progress: ProgressBar? = null
    var directionAdapter:DirectionAdapter?=null

    var rv_direction: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_evening, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gobalData()

        if (sharedPrefsHandler!!.getUserToken()!=null){
            allRecipePresenter?.getAllRecipeResponse("Bearer ${sharedPrefsHandler!!.getUserToken()}" )
        }

    }

    fun gobalData() {
        ac = activity as MainActivity?
        ac!!.showMenu()
        pb_progress = view!!.findViewById(R.id.pb_progress)
        rv_direction = view!!.findViewById(R.id.rv_direction)


        sharedPrefsHandler = SharedPrefsHandler(activity)
        dialogBox = DialogBox(activity!!)
        allRecipePresenter = AllRecipePresenter(this, activity!!)

    }


    companion object {

        fun newInstance(): EveningFragment? {
            return EveningFragment()
        }
    }

    override fun showToast(s: String?) {
        if (s != null) {
            dialogBox?.getDialog(s)
        }
    }

    override fun showProgressBar() {
        pb_progress!!.visibility=View.VISIBLE

    }

    override fun hideProgressBar() {
        pb_progress!!.visibility=View.GONE

    }

    override fun displayGetAllRecipe(allRecipeResponse: AllRecipeResponse) {
        if (InternetConnectionManager.isConnectedToInternet(context!!)){
            if (allRecipeResponse!=null){
                hideProgressBar()
                setRecyclerView(allRecipeResponse.data[0].directions)
            }
        }
    }

    private fun setRecyclerView(directions: List<Direction>) {
        rv_direction?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )


        directionAdapter = DirectionAdapter(activity!!, directions)
        rv_direction?.adapter = directionAdapter

    }

    override fun displayError(s: String?) {
        showToast(s)

    }

    override fun displayExpception(e: Throwable?) {
        hideProgressBar()

    }
}
