package com.example.chatbox.data.repository

import android.util.Log
import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.domain.repository.ChatsRepository
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(private val chatsService: ChatsService) : ChatsRepository {

    override suspend fun getChats(userId: String): Resource<List<ChatModel>?> {

        return try {
            // Show Loading State
            Resource.Loading(true)

            // Get Response From API
            val response = chatsService.fetchChats(userId)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("CheckIfBodyReturned", "getChats: $body")
                Resource.Success(body)
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Something Went Wrong : ${e.message}")
        } finally {

            Log.d("CheckIfWeF'edUp", "We Fucked Up")
            //Hide Loading State
            Resource.Loading(false)
        }


//        val response = chatsService.fetchChats(userId)
//
//        return if (response.isSuccessful) {
//            val body = response.body()!!
//            Resource.Success(body)
//        } else {
//            Resource.Error(response.errorBody()!!.string())
//        }

    }
}