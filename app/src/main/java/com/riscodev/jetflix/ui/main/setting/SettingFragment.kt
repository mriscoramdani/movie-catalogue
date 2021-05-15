package com.riscodev.jetflix.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riscodev.jetflix.R
import com.riscodev.jetflix.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    companion object {
        val TAG: String = SettingFragment::class.java.simpleName

        fun newInstance(): SettingFragment = SettingFragment()
    }

    private lateinit var fragmentSettingBinding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSettingBinding =
            FragmentSettingBinding.inflate(layoutInflater, container, false)
        return fragmentSettingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val versionName = requireActivity().packageManager.getPackageInfo(
            requireActivity().packageName, 0
        ).versionName
        fragmentSettingBinding.versionApp.text = getString(R.string.str_app_version, versionName)
    }
}