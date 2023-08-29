package com.example.chatbox.domain.usecases

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(private val contactsRepository: ContactsRepository){
    suspend operator fun invoke(userId: String): Resource<List<UserDto.Contact>?> {
        return contactsRepository.getContacts(userId)
    }
}