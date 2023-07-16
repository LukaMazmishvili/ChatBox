package com.example.chatbox.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatbox.data.models.StoryModel
import com.example.chatbox.databinding.ItemStoryBinding

class StoriesAdapter : ListAdapter<StoryModel, StoriesAdapter.ViewHolder>(StoriesDiffUtil()) {

    inner class ViewHolder(binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvStoryName
        val image = binding.ivStoryImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemStoryBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.name.text = item.name
        Glide.with(holder.image.context).load(item.image).circleCrop().into(holder.image)
    }

    class StoriesDiffUtil() : DiffUtil.ItemCallback<StoryModel>(){
        override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem == newItem
        }

    }
}