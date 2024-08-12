package com.quang.lilyshop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
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
    private lateinit var OTP: String
    private lateinit var phoneNumber: String
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


    private fun init() {
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

        binding.phoneNumber.text =
            "To confirm your phone number, please enter the otp we sent to $phoneNumber"


        viewModel.countdownTime.observe(this, Observer { millisUntilFinished ->
            if (millisUntilFinished == 0L) {
                binding.resendToken.text = getString(R.string.resend_code)
                binding.resendToken.isClickable = true
            } else {
                val secondsRemaining = millisUntilFinished / 1000
                binding.resendToken.text =
                    "Resend available in ${secondsRemaining}s"
                binding.resendToken.isClickable = false
            }
        })
        viewModel.isResendClickable.observe(this, Observer {
            binding.resendToken.isClickable = it

        })


    }


    private fun settingUpListener() {
        binding.confirmBtn.setOnClickListener {
            // handle confirm button click
            // if all input fields are filled,

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            binding.progressBar.visibility = View.VISIBLE
            binding.confirmBtn.isEnabled = false
            if (binding.et1.text.isNotBlank() && binding.et2.text.isNotBlank() && binding.et3.text.isNotBlank() && binding.et4.text.isNotBlank() && binding.et5.text.isNotBlank() && binding.et6.text.isNotBlank()) {
                val code =
                    "${binding.et1.text}${binding.et2.text}${binding.et3.text}${binding.et4.text}${binding.et5.text}${binding.et6.text}"
                verifyCode(code)


            } else {

                if (binding.et1.requestFocus()) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
                binding.et1.error = ""
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.confirmBtn.isEnabled = true
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.resendToken.setOnClickListener {
            if (viewModel.isResendClickable.value == true) {
                viewModel.resendVerificationCode(phoneNumber, this, callbacks)
            }
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
            editText.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText.text.isEmpty() && index > 0) {
                        etList[index - 1].requestFocus()
                        etList[index - 1].setText("")
                    }
                }
                false
            }
        }

    }


    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(OTP, code)
        signInWithPhoneAuthCredential(credential)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModel.signInWithPhoneAuthCredential(credential, { task ->
            if (task.isSuccessful) {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                    ?: return@signInWithPhoneAuthCredential
                viewModel.checkUserExists(userId, { exists ->
                    if (exists) {
                        // Nếu người dùng đã tồn tại, chuyển đến trang chính
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // Nếu người dùng là mới, chuyển đến trang cung cấp thêm thông tin
                        startActivity(Intent(this, RegisterUserActivity::class.java))
                    }
                    finish()
                }, { exception ->
                    Toast.makeText(
                        this,
                        "Error checking user existence: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            } else {
                Toast.makeText(
                    this,
                    "Sign in failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "Sign in failed: ${task.exception?.message}")
            }
            binding.progressBar.visibility = View.GONE
            binding.confirmBtn.isEnabled = true
        }, { exception ->
            Toast.makeText(this, "Sign in failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Sign in failed: ${exception.message}")
            binding.progressBar.visibility = View.GONE
            binding.confirmBtn.isEnabled = true
        })
    }


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(
                this@OtpVerifyActivity,
                "Verification failed: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            this@OtpVerifyActivity.OTP = verificationId
            viewModel.setResendToken(token)

        }
    }

}