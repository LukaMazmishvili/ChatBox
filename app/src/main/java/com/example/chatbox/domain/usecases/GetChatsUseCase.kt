package com.example.chatbox.domain.usecases

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.data.repository.ChatsRepositoryImpl
import com.example.chatbox.domain.repository.ChatsRepository
import retrofit2.Response
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(private val chatsRepo: ChatsRepository) {

    suspend operator fun invoke(userId: String) : Resource<List<ChatModel>?> {
        return chatsRepo.getChats(userId)
    }

}