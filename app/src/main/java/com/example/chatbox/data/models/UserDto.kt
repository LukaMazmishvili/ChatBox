package com.example.chatbox.data.models

data class UserDto(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val isActive: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val stories: List<Story> = emptyList(),
    val chats: List<Chat> = emptyList(),
    val friends: List<String> = emptyList()
    ) {
    data class Notification(
        val notification: String  // Not Implemented
    )

    data class Story(
        val story: String = ""
    )

    data class Chat(
        val sender: String = "",
        val msg: String = "",
        val img: String = ""
    )
}
