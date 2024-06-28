package com.quang.lilyshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityOrderSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}