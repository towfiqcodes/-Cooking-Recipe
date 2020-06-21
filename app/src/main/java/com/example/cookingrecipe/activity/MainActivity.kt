package com.example.cookingrecipe.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.cookingrecipe.R
import com.example.cookingrecipe.mvp.recipe.fragment.BreakfastFragment
import com.example.cookingrecipe.mvp.recipe.fragment.DinnerFragment
import com.example.cookingrecipe.mvp.recipe.fragment.EveningFragment
import com.example.cookingrecipe.mvp.recipe.fragment.MainFragment
import com.mikhaellopez.circularimageview.CircularImageView
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var ll_menu:LinearLayout?=null
    var tv_breakfast:TextView?=null
    var tv_dinner:TextView?=null
    var tv_evening:TextView?=null

    var ll_layout:LinearLayout?=null
    var  imageView:CircleImageView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_menu= findViewById<LinearLayout>(R.id.ll_menu)
        tv_breakfast= findViewById<TextView>(R.id.tv_breakfast)
        tv_dinner=findViewById<TextView>(R.id.tv_dinner)
        tv_evening=findViewById<TextView>(R.id.tv_evening)

        ll_layout=findViewById(R.id.linearLayout)
        imageView=findViewById(R.id.iv_transition);

        tv_breakfast!!.setOnClickListener(this)
        tv_dinner!!.setOnClickListener(this)
        tv_evening!!.setOnClickListener(this)

        tv_breakfast?.setBackgroundResource(R.drawable.background_shape_yellow)
        tv_breakfast?.setTextColor(resources.getColor(R.color.white))

        val mainFragment:MainFragment= MainFragment.newInstance()!!
        showFragment1(mainFragment)
    }





    protected fun showFragment1(fragment: Fragment) {
        val TAG = fragment.javaClass.simpleName
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_main_container, fragment, TAG) //comment for temp
        fragmentTransaction.commitAllowingStateLoss()
    }


    fun showMenu(){
        ll_menu?.setVisibility(View.VISIBLE)
    }
    fun hideMenu(){
        ll_menu?.setVisibility(View.GONE)
    }

    override fun onClick(v: View?) {
    when(v!!.id){
        R.id.tv_breakfast->{
            tv_breakfast?.setBackgroundResource(R.drawable.background_shape_yellow)
            tv_breakfast?.setTextColor(resources.getColor(R.color.white))

            tv_dinner?.setBackgroundResource(R.drawable.corner)
            tv_dinner?.setTextColor(resources.getColor(R.color.black_pearl))

            tv_evening?.setBackgroundResource(R.drawable.corner)
            tv_evening?.setTextColor(resources.getColor(R.color.black_pearl))

            val breakfastFragment:BreakfastFragment= BreakfastFragment.newInstance()!!
            showFragment(breakfastFragment)

        }
        R.id.tv_dinner->{
            tv_breakfast?.setBackgroundResource(R.drawable.corner)
            tv_breakfast?.setTextColor(resources.getColor(R.color.black_pearl))

            tv_dinner?.setBackgroundResource(R.drawable.background_shape_yellow)
            tv_dinner?.setTextColor(resources.getColor(R.color.white))

            tv_evening?.setBackgroundResource(R.drawable.corner)
            tv_evening?.setTextColor(resources.getColor(R.color.black_pearl))

            val dinnerFragment:DinnerFragment= DinnerFragment.newInstance()

           showFragment2(dinnerFragment)
        }
        R.id.tv_evening->{
            tv_breakfast?.setBackgroundResource(R.drawable.corner)
            tv_breakfast?.setTextColor(resources.getColor(R.color.black_pearl))

            tv_dinner?.setBackgroundResource(R.drawable.corner)
            tv_dinner?.setTextColor(resources.getColor(R.color.black_pearl))

            tv_evening?.setBackgroundResource(R.drawable.background_shape_yellow)
            tv_evening?.setTextColor(resources.getColor(R.color.white))

            val eveningFragment:EveningFragment= EveningFragment.newInstance()!!
            showFragment(eveningFragment)
        }
    }

    }


    /*@Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frmMenuId1);
        if(fragment instanceof MenuListFragment){
            finish();
        }else {
            super.onBackPressed();
        }
    }*/
    override fun onBackPressed() {
        //
        val fragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_container)
        if (fragment is MainFragment) {
            finish()
            val fragment1 = MainFragment()
            val transaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(R.id.activity_main_container, fragment1)
            transaction.addToBackStack(null)
            transaction.commitAllowingStateLoss()
        } else {
            fragment?.let { removeFragment(it) }
            super.onBackPressed()
        }
    }

    fun removeFragment(fragment: Fragment) {
        val TAG = fragment.javaClass.simpleName
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        //        fragmentTransaction.disallowAddToBackStack().replace(R.id.mfs_frame_container, fragment, TAG);
        fragmentTransaction.commit()
        Log.e(
            "remove",
            "remove fragment >>>>>>>." + supportFragmentManager.backStackEntryCount
                .toString()
        )
    }

   /* override fun onResume() {
        super.onResume()

    }*/

    fun getButtonClick(){
        tv_breakfast?.setBackgroundResource(R.drawable.background_shape_yellow)
        tv_breakfast?.setTextColor(resources.getColor(R.color.white))

        tv_dinner?.setBackgroundResource(R.drawable.corner)
        tv_dinner?.setTextColor(resources.getColor(R.color.black_pearl))

        tv_evening?.setBackgroundResource(R.drawable.corner)
        tv_evening?.setTextColor(resources.getColor(R.color.black_pearl))
    }



    protected fun showFragment(fragment: Fragment) {
        var backStack = supportFragmentManager.backStackEntryCount
        if (backStack > 0) {
            while (backStack > 0) {
                supportFragmentManager.popBackStack()
                backStack--
            }
        }
        val TAG = fragment.javaClass.simpleName
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
            .addSharedElement(imageView!!, ViewCompat.getTransitionName(imageView!!)!!)
            .addSharedElement(ll_layout!!, ViewCompat.getTransitionName(ll_layout!!)!!)

            .replace(R.id.activity_main_container, fragment,TAG) //comment for temp
        fragmentTransaction.commit()
    }

    protected fun showFragment2(fragment: Fragment) {
        var backStack = supportFragmentManager.backStackEntryCount
        if (backStack > 0) {
            while (backStack > 0) {
                supportFragmentManager.popBackStack()
                backStack--
            }
        }
        val TAG = fragment.javaClass.simpleName
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
      /*  fragmentTransaction.setCustomAnimations(
            R.anim.slide_from_bottom,
            R.anim.slide_to_top,
            R.anim.slide_from_top,
            R.anim.slide_to_bottom
        )*/
            .addSharedElement(ll_layout!!, ViewCompat.getTransitionName(ll_layout!!)!!)
            .replace(R.id.activity_main_container, fragment, TAG) //comment for temp
        fragmentTransaction.commitAllowingStateLoss()
    }
}