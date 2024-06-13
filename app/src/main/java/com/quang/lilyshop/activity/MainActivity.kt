package com.quang.lilyshop.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.quang.lilyshop.R
import com.quang.lilyshop.activity.Fragment.HomeFragment
import com.quang.lilyshop.activity.Fragment.ProfileFragment
import com.quang.lilyshop.activity.Fragment.SearchFragment
import com.quang.lilyshop.activity.Fragment.WishFragment
import com.quang.lilyshop.databinding.ActivityMainBinding


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
}