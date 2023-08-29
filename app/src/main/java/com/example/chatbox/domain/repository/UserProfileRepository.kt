package com.example.chatbox.domain.repository

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.data.models.UserDto

interface UserProfileRepository {
    suspend fun getUserInfo(userId: String) : Resource<UserDto>
    suspend fun sendFriendRequest(userId: String, data: List<FriendRequestModel>)
}