package com.riscodev.moviecat.ui.main.content.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riscodev.moviecat.databinding.FragmentShowBinding
import com.riscodev.moviecat.utils.EspressoIdlingResource
import com.riscodev.moviecat.viewmodel.ViewModelFactory
import com.riscodev.moviecat.vo.Status

class ShowFragment : Fragment() {

    companion object {
        private const val MENU_TYPE = "menu_type"
        const val MENU_HOME = "menu_home"
        const val MENU_FAVORITE = "menu_favorite"

        fun newInstance(menuType: String) = ShowFragment().apply {
            arguments = Bundle().apply {
                putString(MENU_TYPE, menuType)
            }
        }
    }

    private lateinit var fragmentShowBinding: FragmentShowBinding
    private var menuType: String = MENU_HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(MENU_TYPE)?.apply {
            menuType = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentShowBinding = FragmentShowBinding.inflate(layoutInflater, container, false)
        return fragmentShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val showViewModel = ViewModelProvider(
                this,
                ViewModelFactory.getInstance(requireContext())
            )[ShowViewModel::class.java]

            val showAdapter = ShowAdapter()
            when (menuType) {
                MENU_HOME -> {
                    showViewModel.getShows().observe(viewLifecycleOwner, { showResponse ->
                        when (showResponse.status) {
                            Status.LOADING -> {
                                EspressoIdlingResource.increment()
                                fragmentShowBinding.loadingView.visibility = View.VISIBLE
                            }
                            else -> {
                                fragmentShowBinding.loadingView.visibility = View.GONE
                                if (showResponse.data.isNullOrEmpty()) {
                                    fragmentShowBinding.tvInfo.visibility = View.VISIBLE
                                }
                                else {
                                    showAdapter.submitList(showResponse.data)
                                }
                                EspressoIdlingResource.decrement()
                            }
                        }
                    })
                }
                MENU_FAVORITE -> {
                    showViewModel.getFavoriteShows().observe(viewLifecycleOwner, { movieList ->
                        fragmentShowBinding.loadingView.visibility = View.GONE

                        if (movieList.isNullOrEmpty()) {
                            fragmentShowBinding.tvInfo.visibility = View.VISIBLE
                        }
                        showAdapter.submitList(movieList)
                    })
                }
            }
            with(fragmentShowBinding.rvShow) {
                layoutManager = LinearLayoutManager(activity)
                adapter = showAdapter
            }
        }
    }
}