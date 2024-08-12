package com.quang.lilyshop.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.quang.lilyshop.Model.User
import com.quang.lilyshop.repositoy.UserRepository
import javax.inject.Inject

//@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, phoneNumber: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnCompleteListener
                    checkAndCreateUser(uid, phoneNumber) { success ->
                        if (success) {
                            onSuccess()
                        } else {
                            onFailure(Exception("Failed to create user."))
                        }
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }
    fun checkAndCreateUser(uid: String, phoneNumber: String, onComplete: (Boolean) -> Unit) {
        userRepository.checkUserExits(uid) { exists ->
            if (!exists) {
                val user = User(name = phoneNumber, phoneNumber = phoneNumber, uid = uid)
                userRepository.createUser(user) { success ->
                    onComplete(success)
                }
            } else {
                onComplete(true)
            }
        }
    }
}