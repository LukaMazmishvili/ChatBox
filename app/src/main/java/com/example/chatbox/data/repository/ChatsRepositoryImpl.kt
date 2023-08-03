package com.example.chatbox.data.repository

import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.domain.repository.ChatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(private val chatsService: ChatsService) : ChatsRepository {

    override suspend fun getChats(userId: String): List<ChatModel> {
        return chatsService.fetchChats(userId)
    }

}