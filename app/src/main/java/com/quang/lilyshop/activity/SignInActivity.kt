package com.quang.lilyshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.PhoneAuthViewModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModelFactory
import com.quang.lilyshop.databinding.ActivitySignInBinding
import com.quang.lilyshop.repositoy.PhoneAuthRepository

class SignInActivity : AppCompatActivity() {
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
        binding.sendOTPButton.setOnClickListener{
            sendVerificationCode(binding.phoneEditText.text.toString())
        }

        binding.resendOTPButton.setOnClickListener{
            resendVerificationCode(binding.phoneEditText.text.toString())
        }

        binding.signInButton.setOnClickListener{
            verifyCode(binding.OTPEditText.text.toString())
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@SignInActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("Verification failed: ", "${e.message}")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@SignInActivity.verificationId = verificationId
                viewModel.setResendToken(token)
                binding.resendOTPButton.isEnabled = true
            }
        }

        viewModel.sendVerificationCode(phoneNumber, this, callbacks)
    }

    private fun resendVerificationCode(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@SignInActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
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