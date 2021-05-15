package com.riscodev.jetflix.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.riscodev.jetflix.R
import com.riscodev.jetflix.databinding.ActivityDetailBinding
import com.riscodev.jetflix.ui.detail.movie.DetailMovieFragment
import com.riscodev.jetflix.ui.detail.show.DetailShowFragment
import com.riscodev.jetflix.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONTENT_ID = "extra_content_id"
        const val EXTRA_CONTENT_CATEGORY = "extra_content_category"
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val bundle = intent.extras
        if (bundle != null) {

            val contentId = bundle.getString(EXTRA_CONTENT_ID)
            val contentCategory = bundle.getString(EXTRA_CONTENT_CATEGORY)

            if (contentId.isNullOrEmpty() || contentCategory.isNullOrEmpty()) {
                return
            }

            val detailViewModel = ViewModelProvider(
                this,
                ViewModelFactory.getInstance(this)
            )[DetailViewModel::class.java]

            when (contentCategory) {
                MOVIE -> {
                    detailViewModel.setSelectedMovie(contentId)
                    populateFragment(DetailMovieFragment.newInstance(), DetailMovieFragment.TAG)
                }
                TV_SHOW -> {
                    detailViewModel.setSelectedShow(contentId)
                    populateFragment(DetailShowFragment.newInstance(), DetailShowFragment.TAG)
                }
                else -> {
                }
            }
        }
    }

    private fun populateFragment(mFragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = mFragment
            fragmentTransaction.add(R.id.frame_container, fragment, tag)
        }
        fragmentTransaction.commit()
    }
}