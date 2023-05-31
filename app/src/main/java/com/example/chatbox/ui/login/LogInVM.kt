package com.example.chatbox.ui.login

import androidx.lifecycle.ViewModel
import com.example.chatbox.extentions.validEmailAddress
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LogInVM @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private val _loginState = MutableStateFlow<String>("")
    val loginState = _loginState.asStateFlow()

    fun userLogin(email: String, password: String) {
        if (email.validEmailAddress() && email.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _loginState.value = "Successful"
                }
                .addOnFailureListener {
                    _loginState.value = "Failed"
                }

        }
    }
}