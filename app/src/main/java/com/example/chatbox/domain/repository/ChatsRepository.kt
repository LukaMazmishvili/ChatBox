package com.example.chatbox.domain.repository

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ChatsRepository {
    suspend fun getChats(userId: String) : Resource<List<ChatModel>?>
    suspend fun deleteChat(userId: String, position: Int)
}