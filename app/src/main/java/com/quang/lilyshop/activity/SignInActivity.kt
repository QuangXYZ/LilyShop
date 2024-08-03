package com.quang.lilyshop.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.PhoneAuthViewModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModelFactory
import com.quang.lilyshop.databinding.ActivitySignInBinding
import com.quang.lilyshop.repositoy.PhoneAuthRepository

class SignInActivity : BaseActivity() {
    private val viewModel: PhoneAuthViewModel by viewModels {
        PhoneAuthViewModelFactory(PhoneAuthRepository(FirebaseAuth.getInstance()))
    }
    private lateinit var binding: ActivitySignInBinding
    private lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingUpListener()


    }

    private fun settingUpListener() {

//        binding.sendOTPButton.setOnClickListener {
//            val phoneNumber = binding.phoneEditText.text.toString()
//
//            binding.sendOTPButton.setBackgroundResource(R.drawable.textview_background2)
//            // Thay đổi màu nền thành màu tím sau vài giây
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.sendOTPButton.setBackgroundResource(R.drawable.textview_background)
//            }, 100) // 2000 milliseconds = 1 giây
//
//            if (phoneNumber.isEmpty()) {
//                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            sendVerificationCode("+84$phoneNumber")
//        }
//
//        binding.resendOTPButton.setOnClickListener {
//            val phoneNumber = binding.phoneEditText.text.toString()
//
//            binding.resendOTPButton.setBackgroundResource(R.drawable.textview_background2)
//            // Thay đổi màu nền thành màu tím sau vài giây
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.resendOTPButton.setBackgroundResource(R.drawable.textview_background)
//            }, 100) // 2000 milliseconds = 1 giây
//
//            if (phoneNumber.isEmpty()) {
//                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            resendVerificationCode("+84$phoneNumber")
//        }

        binding.signInButton.setOnClickListener {
//            val otp = binding.OTPEditText.text.toString()
//            if (otp.isEmpty()) {
//                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            verifyCode(otp)
            binding.progressBar.visibility = View.VISIBLE
            binding.signInButton.isEnabled = false
            if (binding.phoneEditText.text?.isEmpty() == true) {
                Toast.makeText(this, "Please Enter phone number", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.signInButton.isEnabled = true
                return@setOnClickListener
            }
            val code = binding.countryCodePicker.selectedCountryCode
            val phone = binding.phoneEditText.text
            sendVerificationCode("+$code$phone")
            Toast.makeText(this, "$code", Toast.LENGTH_SHORT).show()

        }


        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun sendVerificationCode(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(
                    this@SignInActivity,
                    "Verification failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.signInButton.isEnabled = true
                Log.d("Verification failed: ", "${e.message}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@SignInActivity.verificationId = verificationId
                viewModel.setResendToken(token)
                binding.progressBar.visibility = View.GONE
                binding.signInButton.isEnabled = true

                val intent = Intent(this@SignInActivity, OtpVerifyActivity::class.java)
                intent.putExtra("verificationId", verificationId)
                intent.putExtra("phoneNumber", phoneNumber)
                intent.putExtra("resendToken", token)
                startActivity(intent)
            }
        }

        viewModel.sendVerificationCode(phoneNumber, this, callbacks)
    }

    private fun resendVerificationCode(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(
                    this@SignInActivity,
                    "Verification failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@SignInActivity.verificationId = verificationId
                viewModel.setResendToken(token)
            }
        }

        viewModel.resendVerificationCode(phoneNumber, this, callbacks)
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModel.signInWithPhoneAuthCredential(credential, {
            Toast.makeText(this, "Sign in success!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }, {
            Toast.makeText(this, "Sign in failed: ${it.message}", Toast.LENGTH_SHORT).show()
        })
    }




}