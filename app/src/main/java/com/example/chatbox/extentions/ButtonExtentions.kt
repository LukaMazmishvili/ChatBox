package com.example.chatbox.extentions

import android.graphics.Color
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.chatbox.R

fun Button.activate() {
    this.isEnabled = true
    this.setBackgroundResource(R.drawable.btn_activated)
    this.setTextColor(ContextCompat.getColor(this.context,R.color.white))
}

fun Button.deactivate() {
    this.isActivated = false
    this.setBackgroundResource(R.drawable.btn_deactivated)
    this.setTextColor(ContextCompat.getColor(this.context, R.color.applicationColorGray))
}