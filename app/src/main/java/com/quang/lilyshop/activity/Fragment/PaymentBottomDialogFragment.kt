package com.quang.lilyshop.activity.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentHomeBinding
import com.quang.lilyshop.databinding.FragmentPaymentBottomDialogBinding

class PaymentBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPaymentBottomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBottomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        settingUpListener()
    }

    private fun settingUpListener () {
        binding.momo.setOnClickListener {
            binding.check1.visibility = View.VISIBLE
            binding.check2.visibility = View.GONE
            binding.check3.visibility = View.GONE
            binding.momo.strokeColor = context?.resources!!.getColor(R.color.purple)
            binding.ggpay.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
            binding.zlpay.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
        }
        binding.ggpay.setOnClickListener {
            binding.check1.visibility = View.GONE
            binding.check2.visibility = View.VISIBLE
            binding.check3.visibility = View.GONE
            binding.ggpay.strokeColor = context?.resources!!.getColor(R.color.purple)
            binding.momo.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
            binding.zlpay.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
        }
        binding.zlpay.setOnClickListener {
            binding.check1.visibility = View.GONE
            binding.check2.visibility = View.GONE
            binding.check3.visibility = View.VISIBLE
            binding.zlpay.strokeColor = context?.resources!!.getColor(R.color.purple)
            binding.ggpay.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
            binding.momo.strokeColor = context?.resources!!.getColor(R.color.lightGrey)
        }
    }





}