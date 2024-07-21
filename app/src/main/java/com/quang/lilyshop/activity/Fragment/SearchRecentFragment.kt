package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.quang.lilyshop.Adapter.RecentSearchAdapter
import com.quang.lilyshop.Helper.OnItemClickListener
import com.quang.lilyshop.ViewModel.SearchRecentViewModel
import com.quang.lilyshop.databinding.FragmentSearchRecentBinding


class SearchRecentFragment : Fragment() {

    private lateinit var binding: FragmentSearchRecentBinding
    private lateinit var historyList: ArrayList<String>
    private lateinit var searchRecentViewModel: SearchRecentViewModel
    private var listener: OnHistoryItemClickListener? = null


    interface OnHistoryItemClickListener {
        fun onHistoryItemClicked(query: String)
    }


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
        onAttachToParentFragment(requireParentFragment());
        searchRecentViewModel = SearchRecentViewModel(requireActivity().application)

        initRecyclerView()
        settingUpListener()





    }

    private fun initRecyclerView () {
        historyList = ArrayList()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.adapter = RecentSearchAdapter(historyList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                listener?.onHistoryItemClicked(historyList[position])

            }

        })
        searchRecentViewModel.getRecentSearch()
        searchRecentViewModel.histories.observe(viewLifecycleOwner, Observer {
            historyList.clear()
            for (i in it) {
                historyList.add(i)
            }
            binding.recyclerView.adapter!!.notifyDataSetChanged()
        })

    }
    private fun settingUpListener() {
        binding.clearAll.setOnClickListener(OnClickListener {
            searchRecentViewModel.clearHistory()
        })

    }


    private fun onAttachToParentFragment(fragment: Fragment) {
        try {
            listener = fragment as OnHistoryItemClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$fragment must implement OnHistoryItemClickListener"
            )
        }
    }
}