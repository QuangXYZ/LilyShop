package com.quang.lilyshop.activity.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.quang.lilyshop.R
import com.quang.lilyshop.activity.MainActivity
import com.quang.lilyshop.activity.SignInActivity
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

        settingUpListeners()
    }

    private fun settingUpListeners() {
        binding.signOut.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(requireContext(), "Sign out success!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        })
    }
}