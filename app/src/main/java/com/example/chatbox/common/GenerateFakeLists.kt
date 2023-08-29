package com.example.chatbox.common

import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.data.models.StoryModel
import com.example.chatbox.data.models.UserDto
import kotlinx.coroutines.processNextEventInCurrentThread

fun chatsList() : List<ChatModel> {

    val chatsList = mutableListOf<ChatModel>()

    for (i in 1..10) {
        chatsList.add(
            ChatModel(
                i-1,
                "https://as1.ftcdn.net/v2/jpg/03/02/88/46/1000_F_302884605_actpipOdPOQHDTnFtp4zg4RtlWzhOASp.jpg",
                "sender${i-1}",
                "lastMessage",
                "20:19",
                1
            )
        )
    }

    return chatsList
}

fun storiesList() : List<StoryModel> {

    val storiesList = mutableListOf<StoryModel>()

    for (i in 1..10) {
        storiesList.add(
            StoryModel(
                "https://as1.ftcdn.net/v2/jpg/03/02/88/46/1000_F_302884605_actpipOdPOQHDTnFtp4zg4RtlWzhOASp.jpg",
                "user ${i}"
            )
        )
    }

    return storiesList
}

fun contactsList() : List<UserDto.Contact> {
    val contactsList = mutableListOf<UserDto.Contact>()

    for (i in 1..10){
        contactsList.add(
            UserDto.Contact(
                userId = "4DHXUyX3d0YlsPo9bjxDXfCUtx53",
                image = "https://as1.ftcdn.net/v2/jpg/03/02/88/46/1000_F_302884605_actpipOdPOQHDTnFtp4zg4RtlWzhOASp.jpg",
                name = "UserName",
                bio = "bioi klaams"
            )
        )
    }

    return contactsList
}