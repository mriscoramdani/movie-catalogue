package com.riscodev.moviecat.ui.main.content.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riscodev.moviecat.R
import com.riscodev.moviecat.data.source.remote.StatusResponse
import com.riscodev.moviecat.databinding.FragmentShowBinding
import com.riscodev.moviecat.ui.main.content.movie.MovieFragment
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
    private var menuType : String = MovieFragment.MENU_HOME

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
            when(menuType) {
                MENU_HOME -> {
                    showViewModel.getShows().observe(viewLifecycleOwner, { showResponse ->
                        showResponse?.let {
                            when(showResponse.status) {
                                Status.LOADING -> showLoading(true)
                                else -> {
                                    showLoading(false)
                                    if (!it.data.isNullOrEmpty()) {
                                        showAdapter.submitList(it.data)
                                        showContent()
                                    } else
                                        showEmptyMessage()
                                }
                            }
                        }
                    })
                }
                MENU_FAVORITE -> {
                    showViewModel.getFavoriteShows().observe(viewLifecycleOwner, { movieList ->
                        movieList?.let {
                            if (it.size > 0) {
                                showAdapter.submitList(it)
                                showContent()
                            } else
                                showEmptyMessage()
                        }
                    })
                }
            }
            with(fragmentShowBinding.rvItem) {
                layoutManager = LinearLayoutManager(activity)
                adapter = showAdapter
            }
        }
    }

    private fun showEmptyMessage() {
        with(fragmentShowBinding) {
            tvInfo.text = getString(R.string.msg_warn_data_empty)
            tvInfo.visibility = View.VISIBLE
        }
    }

    private fun showContent() {
        fragmentShowBinding.rvItem.visibility = View.VISIBLE
    }

    private fun showLoading(state: Boolean) {
        fragmentShowBinding.loadingView.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}