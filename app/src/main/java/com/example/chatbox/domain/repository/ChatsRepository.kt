package com.example.chatbox.domain.repository

import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto

interface ChatsRepository {

    suspend fun getChats(userId: String) : List<ChatModel>

}