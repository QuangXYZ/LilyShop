package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivityLoginMethodsBinding
import com.quang.lilyshop.networkService.ApiState

class LoginMethodsActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginMethodsBinding
    private val googleSignInRequestCode = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingUpListener()
    }

    private fun settingUpListener() {
        binding.phoneLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleLogin.setOnClickListener(View.OnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            startActivityForResult(mGoogleSignInClient.signInIntent, googleSignInRequestCode)
        })

        binding.facebookLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FaceboolkAuthActivity::class.java))
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            googleSignInRequestCode -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this,"login failed. ${e}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            .addOnFailureListener{
                Toast.makeText(this,"Authentication failed.", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE

            }

    }

}