package com.riscodev.moviecat.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.riscodev.moviecat.databinding.ActivitySplashBinding
import com.riscodev.moviecat.ui.main.MainActivity
import com.riscodev.moviecat.utils.EspressoIdlingResource

class SplashActivity : AppCompatActivity() {
    companion object {
        const val TIME_DELAY = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)

        // Redirect to homepage
        EspressoIdlingResource.increment()
        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivityIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
            EspressoIdlingResource.decrement()
        }, TIME_DELAY)
    }
}