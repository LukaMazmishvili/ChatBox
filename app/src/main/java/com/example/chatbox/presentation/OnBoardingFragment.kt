package com.example.chatbox.presentation

import androidx.navigation.fragment.findNavController
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.databinding.FragmentOnBoardingBinding

class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {

    override fun started() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.toLogInFragment())
        }
    }

    override fun observers() {

    }

}