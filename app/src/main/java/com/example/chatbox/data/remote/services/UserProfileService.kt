package com.example.chatbox.data.remote.services

import com.example.chatbox.common.constants.Endpoints.GET_USERINFO_ENDPOINT
import com.example.chatbox.common.constants.Endpoints.SEND_FRIEND_REQUEST_ENDPOINT
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.data.models.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserProfileService {

    @GET(GET_USERINFO_ENDPOINT)
    suspend fun getUserInfo(@Path("userId") userId: String) : Response<UserDto>

    @PUT(SEND_FRIEND_REQUEST_ENDPOINT)
    suspend fun sendFriendRequest(@Path("userId") userId: String, @Path("data") data: List<FriendRequestModel>)
}