package com.example.chatbox.domain.usecases

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.repository.UserProfileRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val userProfileRepo : UserProfileRepository) {

    suspend operator fun invoke(userId: String) : Resource<UserDto> {
        return userProfileRepo.getUserInfo(userId)
    }

}