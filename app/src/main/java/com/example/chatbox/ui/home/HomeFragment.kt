package com.example.chatbox.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.chatsList
import com.example.chatbox.common.storiesList
import com.example.chatbox.databinding.FragmentHomeBinding
import com.example.chatbox.ui.home.adapters.ChatsAdapter
import com.example.chatbox.ui.home.adapters.StoriesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val storiesAdapter by lazy {
        StoriesAdapter()
    }

    private val chatsAdapter by lazy {
        ChatsAdapter()
    }
    override fun started() {

        setUpRecyclerViews()

        //Hide Bottom Navigation View
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.VISIBLE

    }

    override fun listeners() {
    }

    private fun setUpRecyclerViews() {
        with(binding) {
            rvStories.adapter = storiesAdapter
            rvChats.adapter = chatsAdapter
        }

        // Submit Lists to Recycler Views.
        storiesAdapter.submitList(storiesList())
        chatsAdapter.submitList(chatsList())
    }
}