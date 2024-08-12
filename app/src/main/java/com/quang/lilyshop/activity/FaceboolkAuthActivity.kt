package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.quang.lilyshop.Model.UserModel
import com.quang.lilyshop.R
import com.quang.lilyshop.ViewModel.PhoneAuthViewModel
import com.quang.lilyshop.ViewModel.PhoneAuthViewModelFactory
import com.quang.lilyshop.repositoy.PhoneAuthRepository


class FaceboolkAuthActivity : AppCompatActivity() {

    private lateinit var callbackManager : CallbackManager
    private lateinit var auth: FirebaseAuth
    private val viewModel: PhoneAuthViewModel by viewModels {
        PhoneAuthViewModelFactory(PhoneAuthRepository(FirebaseAuth.getInstance()))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faceboolk_auth)



        callbackManager = CallbackManager.Factory.create()
        auth = Firebase.auth



        LoginManager.getInstance().logInWithReadPermissions(this,
                mutableListOf<String>("email")
            )

            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    if (loginResult != null) {
                        handleFacebookAccessToken(loginResult.accessToken)
                    }
                    else {
                        Toast.makeText(baseContext, "Facebook null result", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onCancel() {
                    finish()
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(baseContext, "Facebook ${exception.message}", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    if (task.result.additionalUserInfo?.isNewUser == true) {
                        val name = task.result.user?.displayName.toString()
                        val email = task.result.user?.email.toString()
                        val photoUrl = task.result.user?.photoUrl.toString()
                        val uid = task.result.user?.uid.toString()

                        val user = UserModel(name = name, email = email, picUrl = photoUrl)
                        viewModel.saveNewUser(uid, user, {

                        }, {

                        })
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            // User is signed in
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            // User is signed out
            finish()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}