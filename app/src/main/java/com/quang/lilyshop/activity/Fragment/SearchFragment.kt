package com.quang.lilyshop.activity.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quang.lilyshop.databinding.ActivityMainBinding
import com.quang.lilyshop.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchResultFragment: SearchResultFragment
    private lateinit var filterFragment: FilterFragment
    private lateinit var recentFragment: SearchRecentFragment
    private var activeFragment: Fragment? = null
    private var isFilterOpen = false


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
            if (isFilterOpen) {

                childFragmentManager.beginTransaction().apply {
                    hide(filterFragment!!)
                    activeFragment?.let { it1 -> show(it1) }
                }.commit()
                isFilterOpen = false
            }
            else {
                childFragmentManager.beginTransaction().apply {
                    hide(activeFragment!!)
                    show(filterFragment)
                }.commit()
                isFilterOpen = true

            }
        })

    }


}