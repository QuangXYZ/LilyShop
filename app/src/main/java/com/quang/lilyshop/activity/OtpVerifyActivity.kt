package com.quang.lilyshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.PhoneAuthViewModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModelFactory
import com.quang.lilyshop.databinding.ActivityOtpVerifyBinding
import com.quang.lilyshop.repositoy.PhoneAuthRepository

class OtpVerifyActivity : BaseActivity() {
    private lateinit var binding: ActivityOtpVerifyBinding
    private lateinit var OTP:String
    private lateinit var phoneNumber:String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val viewModel: PhoneAuthViewModel by viewModels {
        PhoneAuthViewModelFactory(PhoneAuthRepository(FirebaseAuth.getInstance()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        settingUpListener()

    }


    private fun init () {
        OTP = intent.getStringExtra("verificationId")!!
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        viewModel.setResendToken(resendToken)

        configOtpEditText(
            binding.et1,
            binding.et2,
            binding.et3,
            binding.et4,
            binding.et5,
            binding.et6
        )

        if (binding.et1.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        binding.phoneNumber.text = "To confirm your phone number, please enter the otp we sent to $phoneNumber"


    }


    private fun settingUpListener() {
        binding.confirmBtn.setOnClickListener {
            // handle confirm button click
            // if all input fields are filled,

            binding.progressBar.visibility = View.GONE
            binding.progressBar.isEnabled = false
            if (binding.et1.text.isNotBlank() && binding.et2.text.isNotBlank() && binding.et3.text.isNotBlank() && binding.et4.text.isNotBlank() && binding.et5.text.isNotBlank() && binding.et6.text.isNotBlank()) {
                val code = "${binding.et1}${binding.et2}${binding.et3}${binding.et4}${binding.et5}${binding.et6}"
                verifyCode(code)
            } else {
                // show error message
                Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.isEnabled = true
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun configOtpEditText(vararg etList: EditText) {
        val afterTextChanged = { index: Int, e: Editable? ->
            val view = etList[index]
            val text = e.toString()

            when (view.id) {
                // first text changed
                etList[0].id -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                }

                // las text changed
                etList[etList.size - 1].id -> {
                    if (text.isEmpty()) etList[index - 1].requestFocus()
                }

                // middle text changes
                else -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                    else etList[index - 1].requestFocus()
                }
            }
            false
        }
        etList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged { afterTextChanged(index, it) }
        }

    }


    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(OTP, code)
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