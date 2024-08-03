package com.quang.lilyshop.ViewModel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.quang.lilyshop.repositoy.PhoneAuthRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

//@HiltViewModel
class PhoneAuthViewModel @Inject constructor(private val repository: PhoneAuthRepository) : ViewModel() {
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
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        repository.resendVerificationCode(phoneNumber, activity, callbacks)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            repository.signWithPhoneAuthCredential(credential, onSuccess, onFailure)
        }
    }

    fun setResendToken(token: PhoneAuthProvider.ForceResendingToken) {
        repository.setResendToken(token)
    }
}