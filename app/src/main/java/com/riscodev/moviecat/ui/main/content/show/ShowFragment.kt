package com.riscodev.moviecat.ui.main.content.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.riscodev.moviecat.R
import com.riscodev.moviecat.databinding.FragmentShowBinding
import com.riscodev.moviecat.ui.main.content.movie.MovieFragment
import com.riscodev.moviecat.viewmodel.ViewModelFactory

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
                    showViewModel.getShows().observe(viewLifecycleOwner, { movieList ->
                        movieList?.let {
                            showAdapter.submitList(it.data)
                        }
                    })
                }
                MENU_FAVORITE -> {
                    showViewModel.getFavoriteShows().observe(viewLifecycleOwner, { movieList ->
                        movieList?.let {
                            showAdapter.submitList(it)
                        }
                    })
                }
            }
            with(fragmentShowBinding.rvItem) {
                layoutManager = LinearLayoutManager(activity)
                adapter = showAdapter
                showContent()
            }
        }
    }

    private fun showInternetProblem(state: Boolean) {
        fragmentShowBinding.tvInfo.text = getString(R.string.msg_warn_internet_con)
        fragmentShowBinding.tvInfo.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showEmptyData() {
        fragmentShowBinding.tvInfo.text = getString(R.string.msg_warn_data_empty)
        fragmentShowBinding.tvInfo.visibility = View.VISIBLE
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