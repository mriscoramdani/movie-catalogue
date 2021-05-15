package com.riscodev.jetflix.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riscodev.jetflix.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    companion object {
        val TAG: String = SettingFragment::class.java.simpleName

        fun newInstance(): SettingFragment = SettingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentSettingBinding =
            FragmentSettingBinding.inflate(layoutInflater, container, false)
        return fragmentSettingBinding.root
    }
}