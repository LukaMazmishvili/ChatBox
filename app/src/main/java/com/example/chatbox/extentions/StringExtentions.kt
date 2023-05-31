package com.example.chatbox.extentions

import android.widget.EditText

fun String.validEmailAddress() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()


