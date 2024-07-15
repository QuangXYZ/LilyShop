package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentWishBinding

class WishFragment : Fragment() {

    private lateinit var binding: FragmentWishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishBinding.inflate(inflater, container, false)
        return binding.root
    }
}