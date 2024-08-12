package com.quang.lilyshop.ViewModel

import android.app.Activity
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.quang.lilyshop.Model.UserModel
import com.quang.lilyshop.repositoy.PhoneAuthRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class PhoneAuthViewModel(private val repository: PhoneAuthRepository) : ViewModel() {

    private val _countdownTime = MutableLiveData<Long>()
    val countdownTime: LiveData<Long> get() = _countdownTime

    private val _isResendClickable = MutableLiveData<Boolean>()
    val isResendClickable: LiveData<Boolean> get() = _isResendClickable

    private var countDownTimer: CountDownTimer? = null

    init {
        startCountdown()
    }
    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        repository.sendVerificationCode(phoneNumber, activity, callbacks)
    }

    fun resendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: OnVerificationStateChangedCallbacks
    ) {
        repository.resendVerificationCode(phoneNumber, activity, callbacks)
        startCountdown()
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            repository.signWithPhoneAuthCredential(credential, onSuccess, onFailure)
        }
    }

    fun setResendToken(token: PhoneAuthProvider.ForceResendingToken) {
        repository.setResendToken(token)
    }

    private fun startCountdown() {
        _isResendClickable.value = false
        _countdownTime.value = 60000
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _countdownTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _countdownTime.value = 0
                _isResendClickable.value = true
            }
        }.start()
    }

    fun checkUserExists(userId: String, onSuccess: (Boolean) -> Unit, onFailure: (Exception) -> Unit) {
        // Implement the check user exists logic
        repository.checkUserExists(userId, onSuccess, onFailure)
    }
    fun saveNewUser(
        userId: String,
        userModel: UserModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Implement the check user exists logic
        repository.saveNewUser(userId,userModel, onSuccess , onFailure)
    }
}