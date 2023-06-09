package com.example.chatbox.ui.onboarding

import androidx.navigation.fragment.findNavController
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.databinding.FragmentOnBoardingBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding>(FragmentOnBoardingBinding::inflate) {


    override fun started() {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment())
        }
    }

    override fun listeners() {
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.toLogInFragment())
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.toSignUpFragment())
        }
    }

}