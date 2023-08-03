package com.example.chatbox.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.usecases.GetChatsUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val getChatsUseCase: GetChatsUseCase) : ViewModel() {

    private val _getChatsState = MutableStateFlow<List<ChatModel>>(emptyList())
    val getChatsState = _getChatsState.asStateFlow()

    fun getChats(userId: String?) {
        userId?.let {
            viewModelScope.launch {
               _getChatsState.value = getChatsUseCase.invoke(userId)
            }
        }
    }

}