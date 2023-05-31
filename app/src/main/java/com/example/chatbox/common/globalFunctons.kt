package com.example.chatbox.common

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.chatbox.common.constants.InputTypes

fun showAuthErrorMessage(input: EditText, heading: TextView, messageText: TextView, msg: String) {
    input.background.setTint(Color.parseColor("#FF2D1B"))
    messageText.setTextColor(Color.parseColor("#FF2D1B"))
    heading.setTextColor(Color.parseColor("#FF2D1B"))
    messageText.text = msg
    messageText.visibility = View.VISIBLE

//    when (type){
//        InputTypes.EMAIL.name -> {
//
//        }
//    }

}

fun hideAuthErrorMessage(input: EditText, heading: TextView, messageText: TextView) {
    input.background.setTint(Color.parseColor("#000000"))
    messageText.setTextColor(Color.parseColor("#000000"))
    heading.setTextColor(Color.parseColor("#24786D"))
    messageText.text = ""
    messageText.visibility = View.GONE
}