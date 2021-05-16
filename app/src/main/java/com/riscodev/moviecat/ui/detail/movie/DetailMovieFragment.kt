package com.riscodev.moviecat.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riscodev.moviecat.R
import com.riscodev.moviecat.data.source.local.entity.MovieEntity
import com.riscodev.moviecat.databinding.FragmentDetailMovieBinding
import com.riscodev.moviecat.ui.detail.DetailViewModel
import com.riscodev.moviecat.ui.detail.base.BaseFragment
import com.riscodev.moviecat.utils.DateParser
import com.riscodev.moviecat.utils.Utils
import com.riscodev.moviecat.viewmodel.ViewModelFactory
import com.riscodev.moviecat.vo.Status

class DetailMovieFragment : BaseFragment() {

    companion object {
        val TAG: String = DetailMovieFragment::class.java.simpleName

        fun newInstance(): DetailMovieFragment = DetailMovieFragment()
    }

    private lateinit var fragmentMovieBinding: FragmentDetailMovieBinding
    private var favorited: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMovieBinding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity == null) {
            return
        }

        val detailViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireContext())
        )[DetailViewModel::class.java]

        detailViewModel.getSelectedMovieId()?.apply {
            detailViewModel.getFavorite(this, MovieEntity.TYPE)
                .observe(viewLifecycleOwner, { favorite ->
                    if (favorite != null) {
                        fragmentMovieBinding.btnFavoriteMovie.setImageResource(R.drawable.ic_favorite_dark)
                        favorited = true
                    }
                })
        }

        detailViewModel.movie.observe(viewLifecycleOwner, { movie ->
            when(movie.status) {
                Status.LOADING -> showLoading(true)
                else -> {
                    showLoading(false)
                    movie.data?.apply {
                        with(fragmentMovieBinding) {
                            tvTitleMovie.text = originalTitle
                            tvGenresMovie.text = Utils.emptyCheck(genres)
                            tvScoreMovie.text = voteAverage.toString()
                            tvDateMovie.text = DateParser.parse(releaseDate, "yyyy-MM-dd")
                            tvDescMovie.text = overview
                            tvStatusMovie.text = Utils.emptyCheck(status)
                            Glide.with(requireActivity())
                                .load(posterPath)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.bg_reload_dark_blue)
                                        .error(R.drawable.bg_broken_image_dark_blue))
                                .into(imageViewMovie)

                            btnShareMovie.setOnClickListener {
                                shareContent(
                                    originalTitle,
                                    overview,
                                    releaseDate
                                )
                            }

                            btnFavoriteMovie.setOnClickListener {
                                favorited = if (!favorited) {
                                    detailViewModel.saveFavorite(movieId, MovieEntity.TYPE)
                                    btnFavoriteMovie.setImageResource(R.drawable.ic_favorite_dark)
                                    true
                                } else {
                                    detailViewModel.removeFavorite(movieId, MovieEntity.TYPE)
                                    btnFavoriteMovie.setImageResource(R.drawable.ic_favorite_outline_dark)
                                    false
                                }
                            }
                        }
                        showContent()
                    } ?: run {
                        showErrorMessage()
                    }
                }
            }
        })
    }

    private fun showErrorMessage() {
        with(fragmentMovieBinding) {
            tvInfo.text = getString(R.string.msg_warn_error)
            tvInfo.visibility = View.VISIBLE
        }
    }

    private fun showContent() {
        with(fragmentMovieBinding) {
            contentLayout.visibility = View.VISIBLE
            btnFavoriteMovie.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state: Boolean) {
        fragmentMovieBinding.loadingInfo.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}