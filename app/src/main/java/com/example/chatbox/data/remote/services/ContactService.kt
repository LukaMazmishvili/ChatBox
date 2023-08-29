package com.example.chatbox.data.remote.services

import com.example.chatbox.common.constants.Endpoints.GET_CONTACTS_ENDPOINT
import com.example.chatbox.data.models.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactService {

    @GET(GET_CONTACTS_ENDPOINT)
    suspend fun getContacts(@Path("userId") path: String) : Response<List<UserDto.Contact>>

}