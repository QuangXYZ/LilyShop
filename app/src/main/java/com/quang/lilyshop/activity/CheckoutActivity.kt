package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderDetailAdapter
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.databinding.ActivityCheckoutBinding

class CheckoutActivity : BaseActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var managementCart: ManagementCart
    private var tax = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initOrderLists()
        calculatePayment()
        settingUpListener()

    }

    private fun init(){
        managementCart = ManagementCart(this)

    }
    private fun initOrderLists() {
        binding.viewOrder.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewOrder.adapter = OrderDetailAdapter(managementCart.getListCart())
    }

    private fun calculatePayment (){
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managementCart.getTotalFee()*percentTax)*100)/100.0
        val total = Math.round((managementCart.getTotalFee()+tax+delivery)*100)/100
        val productTotal = Math.round((managementCart.getTotalFee())*100)/100

        with(binding) {
            orderTotal.text = "$${productTotal}"
            totalTax.text = "$${tax}"
            feeDelivery.text = "$${delivery}"
            totalPayment.text = "$${total}"
            subtotal.text = "$${productTotal}"
        }
    }
    private fun settingUpListener(){
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.changeAddress.setOnClickListener {
            startActivity(Intent(this, AddressSelectionActivity::class.java))
        }
        binding.layoutAddress.setOnClickListener {
            startActivity(Intent(this, AddressSelectionActivity::class.java))
        }
    }


}