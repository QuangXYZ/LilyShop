package com.quang.lilyshop.activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.quang.lilyshop.Helper.ManagementHistory
import com.quang.lilyshop.R
import com.quang.lilyshop.databinding.FragmentSearchBinding


class SearchFragment : Fragment(),SearchRecentFragment.OnHistoryItemClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchResultFragment: SearchResultFragment
    private lateinit var filterFragment: FilterFragment
    private lateinit var recentFragment: SearchRecentFragment
    private var activeFragment: Fragment? = null
    private var isFilterOpen = false
    private lateinit var managementHistory: ManagementHistory




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingUpListener()

        managementHistory = ManagementHistory(requireContext())
        searchResultFragment = SearchResultFragment()
        filterFragment = FilterFragment()
        recentFragment = SearchRecentFragment()
        activeFragment = recentFragment

        childFragmentManager.beginTransaction().apply {
            add(binding.contentFrame.id, recentFragment, "recentFragment").show(recentFragment)
            add(binding.contentFrame.id, filterFragment, "filterFragment").hide(filterFragment)
            add(binding.contentFrame.id, searchResultFragment, "searchResultFragment").hide(searchResultFragment)
        }.commit()



    }

    private fun settingUpListener() {
        binding.searchText.setEndIconOnClickListener(View.OnClickListener {
            toggleFilter()
        })
        binding.searchText.setStartIconOnClickListener {

            recreateRecentFragment()
        }



        binding.searchContent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                performSearch(binding.searchContent.text.toString())

                true
            } else {
                false
            }
        }
    }



    private fun recreateRecentFragment() {
        // Xóa bỏ recentFragment hiện tại
        childFragmentManager.beginTransaction().remove(recentFragment).commitNow()

        // Tạo lại recentFragment
        recentFragment = SearchRecentFragment()
        activeFragment = recentFragment

        // Thêm và hiển thị lại recentFragment mới tạo
        childFragmentManager.beginTransaction().apply {
            add(binding.contentFrame.id, recentFragment, "recentFragment").show(recentFragment)
            hide(filterFragment)
            hide(searchResultFragment)
        }.commit()

        // Cập nhật giao diện người dùng
        binding.searchText.setStartIconDrawable(R.drawable.search)
    }

    private fun toggleFilter() {
        childFragmentManager.beginTransaction().apply {
            if (isFilterOpen) {
                hide(filterFragment)
                activeFragment?.let { show(it) }
            } else {
                hide(activeFragment!!)
                show(filterFragment)
            }
        }.commit()
        isFilterOpen = !isFilterOpen
    }




    private fun performSearch(query: String) {
        managementHistory.addRecent(query)

        childFragmentManager.beginTransaction().apply {
            hide(activeFragment!!)
            hide(filterFragment)
            show(searchResultFragment)
        }.commit()
        activeFragment = searchResultFragment
        binding.searchText.setStartIconDrawable(R.drawable.back)

        // Truyền query cho searchResultFragment để xử lý kết quả tìm kiếm
        searchResultFragment.search(query)
    }

    override fun onHistoryItemClicked(query: String) {
        binding.searchContent.setText(query)
        performSearch(query)
    }


}