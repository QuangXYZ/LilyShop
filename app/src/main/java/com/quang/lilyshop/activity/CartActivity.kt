package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.CartAdapter
import com.quang.lilyshop.Helper.ChangeNumberItemsListener
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        if (managementCart.getListCart().isEmpty()) {
            binding.viewCart.visibility = View.GONE
            binding.emptyCart.visibility = View.VISIBLE
        }
        else {
            binding.viewCart.visibility = View.VISIBLE
            binding.emptyCart.visibility = View.GONE
        }
        initCartList()
        settingUpListener()
        calculateCart()



    }

    fun initCartList() {
        binding.viewCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter = CartAdapter(managementCart.getListCart(),this,
            object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            })

    }
    fun settingUpListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.checkoutBtn.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }

    fun calculateCart (){
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managementCart.getTotalFee()*percentTax)*100)/100.0
        val total = Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100
        val productTotal = Math.round((managementCart.getTotalFee())*100)/100

        with(binding) {
            taxtxt.text = "$${tax}"
            deliverytxt.text = "$${delivery}"
            totaltxt.text = "$${total}"
            subtotalTxt.text = "$${productTotal}"
        }
    }


}