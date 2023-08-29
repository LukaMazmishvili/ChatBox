package com.example.chatbox.domain.repository

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.UserDto

interface ContactsRepository {

    suspend fun getContacts(userId: String) : Resource<List<UserDto.Contact>?>

}