package com.riscodev.jetflix.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riscodev.jetflix.R
import com.riscodev.jetflix.databinding.FragmentFavoriteBinding
import com.riscodev.jetflix.databinding.FragmentHomeBinding
import com.riscodev.jetflix.ui.main.home.HomeFragment
import com.riscodev.jetflix.ui.main.home.HomeTabAdapter

class FavoriteFragment : Fragment() {

    companion object {
        val TAG: String = FavoriteFragment::class.java.simpleName

        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteTabAdapter =
            FavoriteTabAdapter(requireContext(), requireActivity().supportFragmentManager)
        with(fragmentFavoriteBinding) {
            viewPager.adapter = favoriteTabAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
}