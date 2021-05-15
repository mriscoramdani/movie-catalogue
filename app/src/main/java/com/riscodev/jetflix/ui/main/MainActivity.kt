package com.riscodev.jetflix.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.riscodev.jetflix.R
import com.riscodev.jetflix.databinding.ActivityMainBinding
import com.riscodev.jetflix.ui.main.favorite.FavoriteFragment
import com.riscodev.jetflix.ui.main.home.HomeFragment
import com.riscodev.jetflix.ui.main.setting.SettingFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        openPage(HomeFragment.newInstance(), HomeFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homepage -> {
                supportActionBar?.title = "Homepage"
                openPage(HomeFragment.newInstance(), HomeFragment.TAG)
                return true
            }
            R.id.favorite -> {
                supportActionBar?.title = "Favorite"
                openPage(FavoriteFragment.newInstance(), FavoriteFragment.TAG)
            }
            R.id.setting -> {
                supportActionBar?.title = "Setting"
                openPage(SettingFragment.newInstance(), SettingFragment.TAG)
            }
        }
        return false
    }

    private fun openPage(mFragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, mFragment, tag)
        fragmentTransaction.commit()
    }
}