package com.example.chatbox.ui.login

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.hideAuthErrorMessage
import com.example.chatbox.common.showAuthErrorMessage
import com.example.chatbox.databinding.FragmentLogInBinding
import com.example.chatbox.extentions.activate
import com.example.chatbox.extentions.validEmailAddress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInVM by viewModels()

    override fun started() {

    }

    override fun listeners() {
        binding.etEmail.doOnTextChanged { _, _, _, _ ->
            binding.etPassword.doOnTextChanged { _, _, _, _ ->
                binding.btnLogIn.activate()
            }
        }
        binding.btnLogIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (!email.validEmailAddress()) {
                showAuthErrorMessage(
                    binding.etEmail,
                    binding.tvEmailHeading,
                    binding.tvEmailErrorMessage,
                    "Enter Valid Email"
                )
//                signIn(email, password)
            } else {
                hideAuthErrorMessage(
                    binding.etEmail,
                    binding.tvEmailHeading,
                    binding.tvEmailErrorMessage
                )
            }
            if (password.isEmpty() || password.length <= 6) {
                showAuthErrorMessage(
                    binding.etPassword,
                    binding.tvPasswordHeading,
                    binding.tvPasswordErrorMessage,
                    "Enter Valid Password\nmust contain 0-9 and length >6"
                )
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userLogin(email, password)
                viewModel.loginState.collect { loginState ->
                    if (loginState == "Successful") {
                        Toast.makeText(
                            requireActivity(),
                            "Succesfully Signed In",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}