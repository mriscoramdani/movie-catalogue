package com.riscodev.moviecat.ui.detail.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.riscodev.moviecat.R
import com.riscodev.moviecat.data.source.local.entity.ShowEntity
import com.riscodev.moviecat.databinding.FragmentDetailShowBinding
import com.riscodev.moviecat.ui.detail.DetailViewModel
import com.riscodev.moviecat.ui.detail.base.BaseFragment
import com.riscodev.moviecat.utils.DateParser
import com.riscodev.moviecat.utils.Utils
import com.riscodev.moviecat.viewmodel.ViewModelFactory
import com.riscodev.moviecat.vo.Status

class DetailShowFragment : BaseFragment() {

    companion object {
        val TAG: String = DetailShowFragment::class.java.simpleName

        fun newInstance(): DetailShowFragment = DetailShowFragment()
    }

    private lateinit var fragmentShowBinding: FragmentDetailShowBinding
    private var favorited: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentShowBinding = FragmentDetailShowBinding.inflate(layoutInflater, container, false)
        return fragmentShowBinding.root
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

        detailViewModel.getSelectedShowId()?.apply {
            detailViewModel.getFavorite(this, ShowEntity.TYPE)
                .observe(viewLifecycleOwner, { favorite ->
                    if (favorite != null) {
                        fragmentShowBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_dark)
                        favorited = true
                    }
                })
        }

        detailViewModel.getShow().observe(viewLifecycleOwner, { show ->
            when(show.status) {
                Status.LOADING -> showLoading(true)
                else -> {
                    showLoading(false)
                    show.data?.apply {
                        with(fragmentShowBinding) {
                            tvTitle.text = originalName
                            tvSeasons.text = if (Utils.countCheck(numberSeasons)) {
                                getString(R.string.str_seasons, numberSeasons)
                            } else {
                                "..."
                            }
                            tvGenres.text = Utils.emptyCheck(genres)
                            tvScore.text = voteAverage.toString()
                            tvDate.text = DateParser.parse(firstAirDate, "yyyy-MM-dd")
                            tvDesc.text = overview
                            tvStatus.text = Utils.emptyCheck(status)
                            Glide.with(requireActivity())
                                .load(posterPath)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.bg_reload_dark_blue)
                                        .error(R.drawable.bg_broken_image_dark_blue)
                                )
                                .into(imageView)

                            btnShare.setOnClickListener {
                                shareContent(
                                    originalName,
                                    overview,
                                    firstAirDate
                                )
                            }

                            btnFavorite.setOnClickListener {
                                favorited = if (!favorited) {
                                    detailViewModel.saveFavorite(showId, ShowEntity.TYPE)
                                    btnFavorite.setImageResource(R.drawable.ic_favorite_dark)
                                    true
                                } else {
                                    detailViewModel.removeFavorite(showId, ShowEntity.TYPE)
                                    btnFavorite.setImageResource(R.drawable.ic_favorite_outline_dark)
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
        with(fragmentShowBinding) {
            tvInfo.text = getString(R.string.msg_warn_error)
            tvInfo.visibility = View.VISIBLE
        }
    }

    private fun showContent() {
        with(fragmentShowBinding) {
            contentLayout.visibility = View.VISIBLE
            btnFavorite.visibility = View.VISIBLE
        }
    }

    private fun showLoading(state: Boolean) {
        fragmentShowBinding.loadingInfo.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}