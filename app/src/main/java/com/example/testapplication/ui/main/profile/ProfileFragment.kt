package com.example.testapplication.ui.main.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this //serve per LiveData binding
        return binding.root
    }

}