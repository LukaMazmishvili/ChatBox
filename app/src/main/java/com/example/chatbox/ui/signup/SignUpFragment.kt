package com.example.chatbox.ui.signup

import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.hideAuthErrorMessage
import com.example.chatbox.common.showAuthErrorMessage
import com.example.chatbox.databinding.FragmentSignUpBinding
import com.example.chatbox.extentions.activate
import com.example.chatbox.extentions.validEmailAddress
import com.example.chatbox.ui.login.LogInFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val viewModel: SignUpVM by viewModels()

    override fun started() {

        //Hide Bottom Navigation View
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.GONE

    }

    override fun listeners() {
        with(binding) {
            etName.doOnTextChanged { _, _, _, _ ->
                hideAuthErrorMessage(
                    binding.etName,
                    binding.tvNameHeading,
                    binding.tvNameErrorMessage
                )
                etEmail.doOnTextChanged { _, _, _, _ ->
                    hideAuthErrorMessage(
                        binding.etEmail,
                        binding.tvEmailHeading,
                        binding.tvEmailErrorMessage
                    )
                    etPassword.doOnTextChanged { _, _, _, _ ->
                        hideAuthErrorMessage(
                            binding.etPassword,
                            binding.tvPasswordHeading,
                            binding.tvPasswordErrorMessage
                        )
                        etConfirmPassword.doOnTextChanged { _, _, _, _ ->
                            hideAuthErrorMessage(
                                binding.etConfirmPassword,
                                binding.tvConfirmPasswordHeading,
                                binding.tvConfirmPasswordErrorMessage
                            )

                            btnSignUp.activate()
                        }
                    }
                }
            }

            btnSignUp.setOnClickListener {
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val confirmPassword = etConfirmPassword.text.toString().trim()

                if (name.isEmpty()) {
                    showAuthErrorMessage(
                        etName,
                        tvNameHeading,
                        tvNameErrorMessage,
                        "Enter Your Name"
                    )
                }
                if (!email.validEmailAddress()) {
                    showAuthErrorMessage(
                        etEmail,
                        tvEmailHeading,
                        tvEmailErrorMessage,
                        "Enter Valid Email"
                    )
                }
                if (password.isEmpty() || password.length <= 6) {
                    showAuthErrorMessage(
                        etPassword,
                        tvPasswordHeading,
                        tvPasswordErrorMessage,
                        "Enter Valid Password\nmust contain 0-9 and length >6"
                    )
                }
                if (password != confirmPassword) {
                    showAuthErrorMessage(
                        etConfirmPassword,
                        tvConfirmPasswordHeading,
                        tvConfirmPasswordErrorMessage,
                        "Confirmation Password doesn't match"
                    )
                }

                signUp(name, email, password)
            }
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userSignUp(name, email, password)
                viewModel.signUpState.collect { signUpState ->
                    if (signUpState == "Successful") {
                        findNavController().navigate(SignUpFragmentDirections.toHomeFragment())
                    } else if (signUpState == "Failed"){
                        Toast.makeText(
                            requireActivity(),
                            "Registration Failed !",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}