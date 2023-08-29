package com.example.chatbox.data.repository

import android.util.Log
import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.data.remote.services.UserProfileService
import com.example.chatbox.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(private val userProfileService : UserProfileService) : UserProfileRepository {
    override suspend fun getUserInfo(userId: String): Resource<UserDto> {
        return try {
            // Show Loading State
            Resource.Loading(true)

            // Get Response From API
            val response = userProfileService.getUserInfo(userId)

            if (response.isSuccessful) {

                val body = response.body()

                // When list size changes Firebase rewrite its content and turns list into object
                // This code below provides rewriting response body as list in Firebase DB
//                val db = FirebaseDatabase.getInstance().getReference("Users")
//                db.child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats").setValue(
//                    body?.filterNotNull()?.toList()
//                )

                // Return Response Body
                Resource.Success(body!!)
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Something Went Wrong : ${e.message}")
        } finally {
            //Hide Loading State
            Resource.Loading(false)
        }
    }

    override suspend fun sendFriendRequest(userId: String, data: List<FriendRequestModel>) {
        userProfileService.sendFriendRequest(userId, data)
    }


}