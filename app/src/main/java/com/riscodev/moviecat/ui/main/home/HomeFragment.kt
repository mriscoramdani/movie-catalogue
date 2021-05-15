package com.riscodev.moviecat.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riscodev.moviecat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment()
    }

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeTabAdapter =
            HomeTabAdapter(requireContext(), requireActivity().supportFragmentManager)
        with(fragmentHomeBinding) {
            viewPager.adapter = homeTabAdapter
            tabs.setupWithViewPager(viewPager)

        }
    }
}