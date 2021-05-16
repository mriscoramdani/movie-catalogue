package com.riscodev.moviecat.ui.main.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.riscodev.moviecat.R
import com.riscodev.moviecat.ui.main.content.movie.MovieFragment
import com.riscodev.moviecat.ui.main.content.show.ShowFragment
import com.riscodev.moviecat.utils.EspressoIdlingResource

class HomeTabAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_movie,
            R.string.tab_show
        )
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment.newInstance(MovieFragment.MENU_HOME)
            1 -> ShowFragment.newInstance(ShowFragment.MENU_HOME)
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2
}