package com.quang.lilyshop.repositoy

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.quang.lilyshop.Model.UserModel
import java.lang.Exception
import java.util.concurrent.TimeUnit

class PhoneAuthRepository(private val auth: FirebaseAuth) {

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)// Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS)// Timeout and unit
//            .setActivity(activity)// Activity (for callback binding)
//            .setCallbacks(callbacks)// OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        resendToken?.let {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .setForceResendingToken(it)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)// callback's ForceResendingToken
        }
    }

    fun signWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onSuccess: (Task<AuthResult>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(task)

                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    fun setResendToken(token: PhoneAuthProvider.ForceResendingToken) {
        resendToken = token
    }

    fun checkUserExists(
        userId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId)
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(task.result.exists())
            } else {
                task.exception?.let { onFailure(it) }
            }
        }
    }

    fun saveNewUser(
        userId: String,
        userModel: UserModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userRef: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("users").child(userId)

        userRef.setValue(userModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                task.exception?.let { onFailure(it) }
            }
        }
    }

}