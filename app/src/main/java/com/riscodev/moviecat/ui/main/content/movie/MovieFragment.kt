package com.riscodev.moviecat.ui.main.content.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riscodev.moviecat.databinding.FragmentMovieBinding
import com.riscodev.moviecat.utils.EspressoIdlingResource
import com.riscodev.moviecat.viewmodel.ViewModelFactory
import com.riscodev.moviecat.vo.Status

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
                    movieViewModel.getMovies().observe(viewLifecycleOwner, { movieResponse ->
                        when (movieResponse.status) {
                            Status.LOADING -> {
                                EspressoIdlingResource.increment()
                                fragmentMovieBinding.loadingView.visibility = View.VISIBLE
                            }
                            else -> {
                                fragmentMovieBinding.loadingView.visibility = View.GONE
                                if (movieResponse.data.isNullOrEmpty()) {
                                    fragmentMovieBinding.tvInfo.visibility = View.VISIBLE
                                } else {
                                    movieAdapter.submitList(movieResponse.data)
                                }
                                EspressoIdlingResource.decrement()
                            }
                        }
                    })
                }
                MENU_FAVORITE -> {
                    movieViewModel.getFavoriteMovies().observe(viewLifecycleOwner, { movieList ->
                        fragmentMovieBinding.loadingView.visibility = View.GONE

                        if (movieList.isNullOrEmpty()) {
                            fragmentMovieBinding.tvInfo.visibility = View.VISIBLE
                        }
                        movieAdapter.submitList(movieList)
                    })
                }
            }
            with(fragmentMovieBinding.rvMovie) {
                layoutManager = LinearLayoutManager(activity)
                adapter = movieAdapter
            }
        }
    }
}