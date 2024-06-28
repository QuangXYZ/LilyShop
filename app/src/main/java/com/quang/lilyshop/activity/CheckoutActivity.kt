package com.quang.lilyshop.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.OrderDetailAdapter
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Model.AddressModel
import com.quang.lilyshop.Model.OrderModel
import com.quang.lilyshop.ViewModel.CheckoutViewModel
import com.quang.lilyshop.activity.Fragment.PaymentBottomDialogFragment
import com.quang.lilyshop.databinding.ActivityCheckoutBinding

class CheckoutActivity : BaseActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var managementCart: ManagementCart
    private lateinit var viewModel: CheckoutViewModel
    private var tax = 0.0
    private var delivery = 0.0
    private var total = 0.0
    private var address: AddressModel? = null
    private lateinit var paymentSheet: PaymentBottomDialogFragment


    private val selectAddress = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("address", AddressModel::class.java)
            } else {
                result.data?.getParcelableExtra<AddressModel>("address")
            }
            binding.addressName.text = "${address?.fullName} ${address?.phoneNumber}"
            binding.addressStreet.text = "${address?.address}"
            binding.addressProvince.text =
                "${address?.ward}, ${address?.district}, ${address?.city}"
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initOrderLists()
        calculatePayment()
        settingUpListener()

    }

    private fun init() {
        managementCart = ManagementCart(this)
        viewModel = CheckoutViewModel()
        viewModel.getDefaultAddress(onSuccess = { address ->
            binding.addressName.text = "${address?.fullName} ${address?.phoneNumber}"
            binding.addressStreet.text = "${address?.address}"
            binding.addressProvince.text =
                "${address?.ward}, ${address?.district}, ${address?.city}"

        }, onFailure = {
            binding.addressName.text = "No address"

        })

        viewModel.orderSuccess.observe(this, Observer { order ->
            binding.progress.visibility = View.GONE
            Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, OrderSuccessActivity::class.java))
            finish()
        })
        viewModel.orderError.observe(this, Observer { exception ->
            binding.progress.visibility = View.GONE
            Toast.makeText(this, "Failed to place order: ${exception.message}", Toast.LENGTH_SHORT)
                .show()
        })
    }


    private fun initOrderLists() {
        binding.viewOrder.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewOrder.adapter = OrderDetailAdapter(managementCart.getListCart())
    }

    private fun calculatePayment() {
        val percentTax = 0.02
        delivery = 10.0
        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100.0
        total = (Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100).toDouble()
        val productTotal = Math.round((managementCart.getTotalFee()) * 100) / 100

        with(binding) {
            orderTotal.text = "$${productTotal}"
            totalTax.text = "$${tax}"
            feeDelivery.text = "$${delivery}"
            totalPayment.text = "$${total}"
            subtotal.text = "$${productTotal}"
        }
    }

    private fun settingUpListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.changeAddress.setOnClickListener {
            selectAddress.launch(Intent(this, AddressSelectActivity::class.java).apply {
                putExtra("address", address)
            })
        }
        binding.layoutAddress.setOnClickListener {
            selectAddress.launch(Intent(this, AddressSelectActivity::class.java).apply {
                putExtra("address", address)
            })
        }
        binding.paymentBtn.setOnClickListener {
            paymentSheet = PaymentBottomDialogFragment()
            paymentSheet.show(supportFragmentManager, paymentSheet.tag)
        }
        binding.paymentLayout.setOnClickListener {
            paymentSheet = PaymentBottomDialogFragment()
            paymentSheet.show(supportFragmentManager, paymentSheet.tag)
        }

        binding.checkoutBtn.setOnClickListener {
            binding.checkoutBtn.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE
            checkOut()

        }
    }


    private fun checkOut() {
        binding.progress.visibility = View.VISIBLE

        val order = OrderModel(
            "", "", managementCart.getListCart(), delivery, tax, total
        )

        viewModel.saveOrder(order)
    }
}