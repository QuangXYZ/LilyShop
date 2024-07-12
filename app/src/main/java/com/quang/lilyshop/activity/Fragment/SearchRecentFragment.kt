package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.AddressLineAdapter
import com.quang.lilyshop.Adapter.LocationAdapter
import com.quang.lilyshop.Adapter.RecentSearchAdapter
import com.quang.lilyshop.Helper.ManagementCart
import com.quang.lilyshop.Helper.ManagementHistory
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.databinding.FragmentSearchRecentBinding


class SearchRecentFragment : Fragment() {

    private lateinit var binding: FragmentSearchRecentBinding
    private lateinit var managementHistory: ManagementHistory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)


        managementHistory = ManagementHistory(requireContext())



        binding.recyclerView.adapter = RecentSearchAdapter(managementHistory.getListHistory(), object : OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

        })


    }
}