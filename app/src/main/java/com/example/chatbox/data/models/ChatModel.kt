package com.example.chatbox.data.models

data class ChatModel(
    val image: String,
    val title: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val newMessagesCount: Int?
)
