package com.example.chatbox.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.databinding.ItemContactBinding

class ContactsAdapter : ListAdapter<UserDto.Contact, ContactsAdapter.ViewHolder>(ContactsDiffUtil()) {

    var onItemClicked: ((UserDto.Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemContactBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            Glide.with(holder.image.context).load(item.image).circleCrop().into(holder.image)
            holder.name.text = item.name
            holder.bio.text = item.bio

            holder.root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    inner class ViewHolder(binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val image = binding.ivContactPic
        val name = binding.tvContactUsername
        val bio = binding.tvContactBio
    }

    class ContactsDiffUtil() : DiffUtil.ItemCallback<UserDto.Contact>() {
        override fun areItemsTheSame(
            oldItem: UserDto.Contact,
            newItem: UserDto.Contact
        ): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(
            oldItem: UserDto.Contact,
            newItem: UserDto.Contact
        ): Boolean {
            return oldItem == newItem
        }

    }

}