package com.example.chatbox.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.domain.usecases.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val getChatsUseCase: GetChatsUseCase) : ViewModel() {

    private val _getChatsState = MutableStateFlow(ChatsApiState())
    val getChatsState = _getChatsState.asStateFlow()

    fun getChats(userId: String?) {
        userId?.let {
            viewModelScope.launch {
                _getChatsState.value = _getChatsState.value.copy(isLoading = true)
                when (val response = getChatsUseCase.invoke(userId)) {
                    is Resource.Success -> {
                        _getChatsState.value = _getChatsState.value.copy(
                            isLoading = false,
                            data = response.data,
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Error -> {
                        _getChatsState.value = _getChatsState.value.copy(
                            isLoading = false,
                            error = response.errorMsg
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }

                    is Resource.Loading -> {
                        _getChatsState.value = _getChatsState.value.copy(
                            isLoading = true
                        )
                        Log.d("CheckIfCodeWorks", "getChats: $response")
                    }
                }

//                emit(Resource.Loading(true))
//                val response = getChatsUseCase.invoke(userId)
//                if (response.isSuccessful){
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        emit(Resource.Success(responseBody))
//                        emit(Resource.Loading(false))
//                    }
//                } else {
//                    val errorBody = response.errorBody()
//                    emit(Resource.Loading(false))
//                    emit(Resource.Error("Something Went Wrong! ${errorBody?.string()}"))
//                }
            }
        }
    }

    data class ChatsApiState(
        val isLoading: Boolean = false,
        val data: List<ChatModel>? = null,
        val error: String = ""
    )
}