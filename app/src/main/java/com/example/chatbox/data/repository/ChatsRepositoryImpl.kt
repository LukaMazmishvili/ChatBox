package com.example.chatbox.data.repository

import android.util.Log
import com.example.chatbox.common.Resource
import com.example.chatbox.common.chatsList
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.domain.repository.ChatsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
                Log.d("CheckIfBodyReturned", "getChats: ${body!!.filterNotNull()}")

                // When list size changes Firebase rewrite its content and turns list into object
                // This code below provides rewriting response body as list in Firebase DB
                val db = FirebaseDatabase.getInstance().getReference("Users")
                db.child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats").setValue(
                    body.filterNotNull().toList()
                )

                // Return Response Body
                Resource.Success(body.filterNotNull().toList())
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