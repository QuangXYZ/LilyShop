package com.quang.lilyshop.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.quang.lilyshop.Model.UserModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModelFactory

import com.quang.lilyshop.databinding.ActivityRegisterUserBinding
import com.quang.lilyshop.repositoy.PhoneAuthRepository

class RegisterUserActivity : BaseActivity() {
    private lateinit var binding : ActivityRegisterUserBinding
    private val viewModel: PhoneAuthViewModel by viewModels {
        PhoneAuthViewModelFactory(PhoneAuthRepository(FirebaseAuth.getInstance()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        settingUpListener()
    }

    private fun init() {

    }

    private fun settingUpListener() {
        binding.submitBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            var user: UserModel = UserModel(name = name, email = email)
            viewModel.saveNewUser(userId,user, {
                startActivity(Intent(this, MainActivity::class.java))
            }, {
                Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE

            })
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}