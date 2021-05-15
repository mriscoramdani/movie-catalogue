package com.riscodev.jetflix.ui.detail.base

import android.content.Intent
import androidx.fragment.app.Fragment
import com.riscodev.jetflix.R
import java.util.*

open class BaseFragment : Fragment() {

    fun shareContent(title: String, desc: String, genres: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT, """
            Let's go Watch !!
            ${title.toUpperCase(Locale.ROOT)}
            $desc
            
            genres : $genres
        """.trimIndent()
        )
        startActivity(Intent.createChooser(shareIntent, getString(R.string.str_share_with)))
    }
}