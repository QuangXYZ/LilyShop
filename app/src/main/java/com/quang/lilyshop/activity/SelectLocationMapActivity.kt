package com.quang.lilyshop.activity

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quang.lilyshop.Adapter.AddressAdapter
import com.quang.lilyshop.Adapter.LocationAdapter
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.ActivitySelectLocationMapBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.util.Locale

class SelectLocationMapActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivitySelectLocationMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var addressList:MutableList<Address>
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var geocoder:Geocoder

    private val searchJob = Job()
    private val searchScope = CoroutineScope(Dispatchers.Main + searchJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        geocoder = Geocoder(applicationContext)
        var mapFragment: SupportMapFragment = getSupportFragmentManager().findFragmentById(R.id.map_fragment) as SupportMapFragment
        addressList = mutableListOf()
         locationAdapter = LocationAdapter(addressList, object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                    val address = addressList[position]
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(address.getAddressLine(0)))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

            }

        })
        binding.listView.adapter = locationAdapter

        binding.listView.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(binding.listView.context, LinearLayoutManager.VERTICAL)
        binding.listView.addItemDecoration(dividerItemDecoration)
        binding.search.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchScope.launch {
                    debounceSearch(newText)
                }
                return true
            }

        })
        mapFragment.getMapAsync(this)


        binding.backBtn.setOnClickListener {
            finish()
        }
    }


    private suspend fun debounceSearch(query: String?) {
        flowOf(query)
            .debounce(300) // Chờ 300ms sau khi người dùng ngừng nhập
            .distinctUntilChanged() // Chỉ xử lý khi văn bản thay đổi
            .collectLatest {
                searchLocation(it)
            }
    }

    private fun searchLocation(query: String?) {
        if(query != null) {
            try {
                addressList = geocoder.getFromLocationName(query, 5)?.filter {
                    it.countryCode.equals("VN", ignoreCase = true)
                } as MutableList<Address>
                locationAdapter.updateAddresses(addressList)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.setOnMapClickListener { latLng ->
            handleMapClick(latLng)
        }
    }

    private fun handleMapClick(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses!=null) {
            val address = addresses[0]
            addressList.clear()
            addressList.add(address)
            locationAdapter.updateAddresses(addressList)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title(address.getAddressLine(0)))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    override fun onDestroy() {
        super.onDestroy()
        searchJob.cancel() // Hủy coroutine khi Activity bị hủy
    }
}