package com.riscodev.moviecat.ui.main.content.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.riscodev.moviecat.R
import com.riscodev.moviecat.databinding.FragmentMovieBinding
import com.riscodev.moviecat.viewmodel.ViewModelFactory

class MovieFragment : Fragment() {

    companion object {
        private const val MENU_TYPE = "menu_type"
        const val MENU_HOME = "menu_home"
        const val MENU_FAVORITE = "menu_favorite"

        fun newInstance(menuType: String) = MovieFragment().apply {
            arguments = Bundle().apply {
                putString(MENU_TYPE, menuType)
            }
        }
    }

    private lateinit var fragmentMovieBinding: FragmentMovieBinding
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
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val movieViewModel = ViewModelProvider(
                this,
                ViewModelFactory.getInstance(requireContext())
            )[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()

            when (menuType) {
                MENU_HOME -> {
                    movieViewModel.getMovies().observe(viewLifecycleOwner, { movieList ->
                        movieList?.let {
                            movieAdapter.submitList(it.data)
                        }
                    })
                }
                MENU_FAVORITE -> {
                    movieViewModel.getFavoriteMovies().observe(viewLifecycleOwner, { movieList ->
                        movieList?.let {
                            movieAdapter.submitList(it)
                        }
                    })
                }
            }
            with(fragmentMovieBinding.rvItem) {
                layoutManager = LinearLayoutManager(activity)
                adapter = movieAdapter
                showContent()
            }
        }
    }

    private fun showInternetProblem(state: Boolean) {
        fragmentMovieBinding.tvInfo.text = getString(R.string.msg_warn_internet_con)
        fragmentMovieBinding.tvInfo.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showEmptyData() {
        fragmentMovieBinding.tvInfo.text = getString(R.string.msg_warn_data_empty)
        fragmentMovieBinding.tvInfo.visibility = View.VISIBLE
    }

    private fun showContent() {
        fragmentMovieBinding.rvItem.visibility = View.VISIBLE
    }

    private fun showLoading(state: Boolean) {
        fragmentMovieBinding.loadingView.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}