package com.example.chatbox.domain.usecases

import com.example.chatbox.common.Resource
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.domain.repository.UserProfileRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(private val userProfileRepository: UserProfileRepository) {

    suspend operator fun invoke(userId: String, data: List<FriendRequestModel>) {
        userProfileRepository.sendFriendRequest(userId, data)
    }

}