package com.quang.lilyshop.activity.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.quang.lilyshop.R
import com.quang.lilyshop.activity.CartActivity
import com.quang.lilyshop.activity.CheckoutActivity
import com.quang.lilyshop.activity.FilterActivity
import com.quang.lilyshop.databinding.FragmentHomeBinding
import com.quang.lilyshop.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingUpListener()
    }

    private fun settingUpListener() {
        binding.searchText.setEndIconOnClickListener(View.OnClickListener {
            activity?.startActivity(Intent(context, FilterActivity::class.java))

        })

    }


}