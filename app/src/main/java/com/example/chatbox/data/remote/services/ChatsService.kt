package com.example.chatbox.data.remote.services

import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatsService {

    @GET("Users/{userID}/chats.json")
    suspend fun fetchChats(@Path("userID") userId: String) : List<ChatModel>

}