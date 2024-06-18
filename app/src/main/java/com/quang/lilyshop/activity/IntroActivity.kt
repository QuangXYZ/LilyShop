package com.quang.lilyshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivityIntroBinding
import com.quang.lilyshop.databinding.ActivitySignInBinding

class IntroActivity : BaseActivity() {
    private lateinit var binding :ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }


}