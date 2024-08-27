package com.quang.lilyshop.activity

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.quang.lilyshop.Helper.DataLocalManager
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.Model.UserModel
import com.quang.lilyshop.R
import com.quang.lilyshop.activity.Fragment.HomeFragment
import com.quang.lilyshop.activity.Fragment.ProfileFragment
import com.quang.lilyshop.activity.Fragment.SearchFragment
import com.quang.lilyshop.activity.Fragment.WishFragment
import com.quang.lilyshop.databinding.ActivityMainBinding
import com.quang.lilyshop.repositoy.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var wishFragment: WishFragment
    private lateinit var profileFragment: ProfileFragment
    private var activeFragment: Fragment? = null

    private var curPos = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {saveUser()}





            homeFragment = HomeFragment()
            searchFragment = SearchFragment()
            wishFragment = WishFragment()
            profileFragment = ProfileFragment()
            activeFragment = homeFragment

            supportFragmentManager.beginTransaction().apply {
                add(binding.contentFrame.id, homeFragment, "HomeFragment").show(homeFragment)
                add(binding.contentFrame.id, searchFragment, "SearchFragment").hide(searchFragment)
                add(binding.contentFrame.id, wishFragment, "wishFragment").hide(wishFragment)
                add(binding.contentFrame.id, profileFragment, "ProfileFragment").hide(profileFragment)
            }.commit()

        settingUpListener()
    }


    fun settingUpListener() {
        binding.bottomBar.setOnItemSelectedListener { item ->

            when (item) {
                0 -> {
                    switchFragment(homeFragment, curPos, 0)
                    curPos = 0

                }

                1 -> {
                    switchFragment(searchFragment, curPos, 1)
                    curPos = 1

                }

                2 -> {
                    switchFragment(wishFragment, curPos, 2)
                    curPos = 2

                }

                3 -> {
                    switchFragment(profileFragment, curPos, 3)
                    curPos = 3

                }

                else -> false
            }

        }

    }

    private fun switchFragment(fragment: Fragment, curPos: Int, newPos: Int) {
        if (fragment != activeFragment) {
            lateinit var curAnim: Animation
            lateinit var newAnim: Animation
            if (newPos > curPos) {
                curAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_slide_in_left)
                newAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_slide_out_left)
            } else {
                curAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_slide_in_right)
                newAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_slide_out_right)
            }
            // Set animation for fragment entering and exiting
            fragment.view?.startAnimation(curAnim)
            activeFragment?.view?.startAnimation(newAnim)
            supportFragmentManager.beginTransaction().apply {

                hide(activeFragment!!)
                show(fragment)
            }.commit()
            activeFragment = fragment
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    suspend fun saveUser() {
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return
        val userRef = database.child("users").child(userId)

        try {
            val snapshot = userRef.get().await()
            val user = snapshot.getValue(UserModel::class.java)
            DataLocalManager.user = user
        } catch (e: Exception) {
            // Handle exceptions here
        }
    }
}