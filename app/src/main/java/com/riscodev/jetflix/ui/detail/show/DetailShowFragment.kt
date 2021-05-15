package com.riscodev.jetflix.ui.detail.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.riscodev.jetflix.R
import com.riscodev.jetflix.data.source.local.entity.ShowEntity
import com.riscodev.jetflix.databinding.FragmentDetailShowBinding
import com.riscodev.jetflix.ui.detail.DetailViewModel
import com.riscodev.jetflix.ui.detail.base.BaseFragment
import com.riscodev.jetflix.utils.DateParser
import com.riscodev.jetflix.viewmodel.ViewModelFactory

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
            show.data?.apply {
                with(fragmentShowBinding) {
                    tvTitle.text = originalName
                    tvSeasons.text = getString(R.string.str_seasons, numberSeasons)
                    tvGenres.text = genres
                    tvScore.text = voteAverage.toString()
                    tvDate.text = DateParser.parse(firstAirDate, "yyyy-MM-dd")
                    tvDesc.text = overview
                    tvStatus.text = status
                    Glide.with(requireActivity())
                        .load(posterPath)
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
            }
        })
    }
}