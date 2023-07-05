package com.example.chatbox.ui.signup

import androidx.lifecycle.ViewModel
import com.example.chatbox.data.models.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase
) : ViewModel() {

    private val _signUpState = MutableStateFlow<String>("")
    val signUpState = _signUpState.asStateFlow()

    fun userSignUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser?.uid?.let { userId ->
                    addUserInDB(name, email, userId)
                }
                _signUpState.value = "Successful"
            }
            .addOnFailureListener {
                _signUpState.value = "Failed"
            }
    }

    private fun addUserInDB(name: String, email: String, userID: String) {
        db.getReference("Users")
            .child(userID)
            .setValue(
                UserDto(
                    name = name,
                    email = email
                )
            )
    }

}