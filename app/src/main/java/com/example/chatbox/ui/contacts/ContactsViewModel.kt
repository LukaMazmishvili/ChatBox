package com.example.chatbox.ui.contacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.usecases.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val getContactsUseCase: GetContactsUseCase) :
    ViewModel() {

    private val _getContactsState = MutableStateFlow(ContactApiState())
    val getContactsState = _getContactsState.asStateFlow()

    fun getContacts(userId: String?) {
        userId?.let {
            viewModelScope.launch {
                _getContactsState.value = _getContactsState.value.copy(isLoading = true)
                when (val response = getContactsUseCase.invoke(userId)) {
                    is Resource.Success -> {
                        _getContactsState.value = _getContactsState.value.copy(
                            isLoading = false,
                            data = response.data?.toList(),
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Error -> {
                        _getContactsState.value = _getContactsState.value.copy(
                            isLoading = false,
                            error = response.errorMsg
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Loading -> {
                        _getContactsState.value = _getContactsState.value.copy(
                            isLoading = true
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }
                }
            }
        }
    }

    data class ContactApiState(
        val isLoading: Boolean = false,
        val data: List<UserDto.Contact>? = null,
        val error: String = ""
    )

}