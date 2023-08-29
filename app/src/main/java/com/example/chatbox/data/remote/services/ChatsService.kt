package com.example.chatbox.data.remote.services


import com.example.chatbox.common.constants.Endpoints.GET_CHATS_ENDPOINT
import com.example.chatbox.common.constants.Endpoints.DELETE_CHAT_ENDPOINT
import com.example.chatbox.data.models.ChatModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatsService {
    @GET(GET_CHATS_ENDPOINT)
    suspend fun fetchChats(@Path("userID") userId: String): Response<List<ChatModel>?>

    @DELETE(DELETE_CHAT_ENDPOINT)
    suspend fun deleteChat(@Path("userId") userId: String, @Path("chatId") chatId: Int): Response<*>
}