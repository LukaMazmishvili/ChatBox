package com.example.chatbox.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.databinding.FragmentOnBoardingBinding

class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    override fun started() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLogInFragment())
        }
    }

    override fun observers() {

    }

}