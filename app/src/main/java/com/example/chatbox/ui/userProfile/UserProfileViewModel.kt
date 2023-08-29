package com.example.chatbox.ui.userProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.usecases.GetContactsUseCase
import com.example.chatbox.domain.usecases.GetUserInfoUseCase
import com.example.chatbox.domain.usecases.SendFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserInfoUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
) :
    ViewModel() {

    private val _getUserProfile = MutableStateFlow(UserInfoApiState())
    val getUserProfile = _getUserProfile.asStateFlow()

    fun getUserInfo(userId: String?) {
        userId?.let {
            viewModelScope.launch {
                _getUserProfile.value = _getUserProfile.value.copy(isLoading = true)
                when (val response = getUserProfileUseCase.invoke(userId)) {
                    is Resource.Success -> {
                        _getUserProfile.value = _getUserProfile.value.copy(
                            isLoading = false,
                            data = response.data,
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Error -> {
                        _getUserProfile.value = _getUserProfile.value.copy(
                            isLoading = false,
                            error = response.errorMsg
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Loading -> {
                        _getUserProfile.value = _getUserProfile.value.copy(
                            isLoading = true
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }
                }
            }
        }
    }

    fun sendFriendRequest(userId: String, data: List<FriendRequestModel>) {
        viewModelScope.launch {
            sendFriendRequestUseCase.invoke(userId, data)
        }
    }

    data class UserInfoApiState(
        val isLoading: Boolean = false,
        val data: UserDto? = null,
        val error: String = ""
    )

}