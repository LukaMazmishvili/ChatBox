package com.example.chatbox.common.constants

object Endpoints {

    const val BASE_URL = "https://chatbox-b25a2-default-rtdb.firebaseio.com/"

    const val GET_CHATS_ENDPOINT = "Users/{userID}/chats.json"
    const val DELETE_CHAT_ENDPOINT = "Users/{userId}/chats/{chatId}.json"
    const val GET_CONTACTS_ENDPOINT = "Users/{userId}/contact.json"
    const val GET_USERINFO_ENDPOINT = "Users/{userId}.json"
    const val SEND_FRIEND_REQUEST_ENDPOINT = "Users/{userId}/notifications/{data}.json"

}