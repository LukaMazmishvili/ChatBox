package com.example.chatbox.ui.home.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatbox.data.models.ChatModel
import com.example.chatbox.databinding.ItemChatBinding

class ChatsAdapter: ListAdapter<ChatModel, ChatsAdapter.ViewHolder>(ChatsDiffUtil()) {

    var onItemClicked : ((ChatModel) -> Unit)? = null
    var onItemLongClicked : ((ChatModel) -> Unit)? = null

    inner class ViewHolder(binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val image = binding.ivChatSenderImg
        val title = binding.tvSenderName
        val lastMessage = binding.tvLastMessage
        val lastMessageSentTime = binding.tvLastMessageSendTime
    }

    class ChatsDiffUtil() : DiffUtil.ItemCallback<ChatModel>(){
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemChatBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = getItem(position)
        Log.d("CheckItemInAdapter", "onBindViewHolder: $item")

        item?.let {
            Glide.with(holder.image.context).load(item.image).circleCrop().into(holder.image)
            holder.title.text = item.title
            holder.lastMessage.text = item.lastMessage
            holder.lastMessageSentTime.text = item.lastMessageTime

            holder.root.setOnLongClickListener {
                onItemLongClicked?.invoke(item)
                true
            }

            holder.root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}