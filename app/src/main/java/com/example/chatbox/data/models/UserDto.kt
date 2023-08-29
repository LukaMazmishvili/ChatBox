package com.example.chatbox.data.models

data class UserDto(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val bio: String = "Hello ChatBox :)",
    val isActive: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val stories: List<Story>? = null,
    val chats: List<ChatModel?>? = null,
    val contacts: List<Contact>? = null
    ) {
    data class Notification(
        val notification: String  // Not Implemented
    )

    data class Story(
        val story: String = ""
    )

    data class Contact(
        val userId: String = "",
        val image: String = "",
        val name: String = "",
        val bio: String
    )
}
