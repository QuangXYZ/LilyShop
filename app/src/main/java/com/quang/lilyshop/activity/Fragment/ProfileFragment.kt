package com.quang.lilyshop.activity.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quang.lilyshop.R

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.quang.lilyshop.Helper.DataLocalManager
import com.quang.lilyshop.activity.IntroActivity
import com.quang.lilyshop.activity.OrderManagerActivity
import com.quang.lilyshop.activity.ReviewManagerActivity
import com.quang.lilyshop.databinding.FragmentProfileBinding


class ProfileFragment() : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        settingUpListeners()

    }

    private fun init() {
        val user = DataLocalManager.user
        if (user != null) {
            binding.name.text = user.name
            if (user.picUrl != "") {
                Glide.with(this)
                    .load(user.picUrl)
                    .into(binding.avatar)
            }
            else {
                binding.avatar.setImageResource(R.drawable.user_default)
            }
        }
        else {
            binding.name.text = "Guest"
            binding.avatar.setImageResource(R.drawable.user_default)
        }







    }

    private fun settingUpListeners() {

        binding.ratingProduct.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context, ReviewManagerActivity::class.java))
        })
        binding.logout.setOnClickListener(View.OnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { dialog, which ->
                    val intent = Intent(context, IntroActivity::class.java)
                    Firebase.auth.signOut()
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out)
                    requireActivity().finish()
                }.setNegativeButton("No") { dialog, which -> }.show()
        })

        binding.order.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context,  OrderManagerActivity::class.java))
        })
    }




    }
