package com.riscodev.jetflix.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.riscodev.jetflix.R
import com.riscodev.jetflix.data.source.local.entity.MovieEntity
import com.riscodev.jetflix.databinding.FragmentDetailMovieBinding
import com.riscodev.jetflix.ui.detail.DetailViewModel
import com.riscodev.jetflix.ui.detail.base.BaseFragment
import com.riscodev.jetflix.utils.DateParser
import com.riscodev.jetflix.viewmodel.ViewModelFactory

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
                        fragmentMovieBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_dark)
                        favorited = true
                    }
                })
        }

        detailViewModel.getMovie().observe(viewLifecycleOwner, { movie ->
            movie.data?.apply {
                with(fragmentMovieBinding) {
                    tvTitle.text = originalTitle
                    tvGenres.text = ""
                    tvScore.text = ""
                    tvDate.text = DateParser.parse(releaseDate, "yyyy-MM-dd")
                    tvDesc.text = overview
                    Glide.with(requireActivity())
                        .load(posterPath)
                        .into(imageView)

                    btnShare.setOnClickListener {
                        shareContent(
                            originalTitle,
                            overview,
                            releaseDate
                        )
                    }

                    btnFavorite.setOnClickListener {
                        favorited = if (!favorited) {
                            detailViewModel.saveFavorite(movieId, MovieEntity.TYPE)
                            btnFavorite.setImageResource(R.drawable.ic_favorite_dark)
                            true
                        } else {
                            detailViewModel.removeFavorite(movieId, MovieEntity.TYPE)
                            btnFavorite.setImageResource(R.drawable.ic_favorite_outline_dark)
                            false
                        }
                    }
                }
            }
        })
    }
}