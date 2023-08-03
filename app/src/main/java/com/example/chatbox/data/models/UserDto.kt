package com.example.chatbox.data.models

data class UserDto(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val isActive: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val stories: List<Story>? = null,
    val chats: List<ChatModel>? = listOf<ChatModel>(),
    val friends: List<String>? = null
    ) {
    data class Notification(
        val notification: String  // Not Implemented
    )

    data class Story(
        val story: String = ""
    )

    data class Chat (
        val id: String
//        val chatId: String = "",
//        val sender: String = "",
//        val msg: String = "",
//        val img: String = ""
    )
}
