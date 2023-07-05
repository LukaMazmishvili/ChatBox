package com.example.chatbox.ui.login

import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.hideAuthErrorMessage
import com.example.chatbox.common.showAuthErrorMessage
import com.example.chatbox.databinding.FragmentLogInBinding
import com.example.chatbox.extentions.activate
import com.example.chatbox.extentions.validEmailAddress
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInVM by viewModels()

    override fun started() {

        //Hide Bottom Navigation View
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.GONE

    }

    override fun listeners() {
        binding.etEmail.doOnTextChanged { _, _, _, _ ->

            hideAuthErrorMessage(
                binding.etEmail,
                binding.tvEmailHeading,
                binding.tvEmailErrorMessage
            )

            binding.etPassword.doOnTextChanged { _, _, _, _ ->

                hideAuthErrorMessage(
                    binding.etPassword,
                    binding.tvPasswordHeading,
                    binding.tvPasswordErrorMessage
                )

                binding.btnLogIn.activate()
            }
        }
        binding.btnLogIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (password.isEmpty() || password.length <= 6) {
                showAuthErrorMessage(
                    binding.etPassword,
                    binding.tvPasswordHeading,
                    binding.tvPasswordErrorMessage,
                    "Enter Valid Password\nmust contain 0-9 and length > 6"
                )
            }

            if (!email.validEmailAddress()) {
                Log.d("emailValidation", email.validEmailAddress().toString())
                showAuthErrorMessage(
                    binding.etEmail,
                    binding.tvEmailHeading,
                    binding.tvEmailErrorMessage,
                    "Enter Valid Email"
                )
            }

            signIn(email, password)
        }
    }

    private fun signIn(email: String, password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userLogin(email, password)
                viewModel.loginState.collect { loginState ->
                    when (loginState) {
                        "Successful" -> {
                            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                        }
                    }
                }
            }
        }
    }
}