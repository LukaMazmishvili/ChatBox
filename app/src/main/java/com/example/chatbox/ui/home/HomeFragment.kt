package com.example.chatbox.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.chatsList
import com.example.chatbox.common.storiesList
import com.example.chatbox.data.models.UserDto
import com.example.chatbox.databinding.FragmentHomeBinding
import com.example.chatbox.ui.home.adapters.ChatsAdapter
import com.example.chatbox.ui.home.adapters.StoriesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeVM by viewModels()

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

//        observer()
        checkDataBaseBranch()

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
    }

    private fun checkDataBaseBranch() {
        val state = FirebaseDatabase.getInstance().getReference("Users")

        state.child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        observer()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun observer() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getChats(currentUserId)

                viewModel.getChatsState.collect { chatsList ->
                    if (chatsList.isNotEmpty()) {
                        binding.tvNoConversations.visibility = View.GONE
                        binding.rvChats.visibility = View.VISIBLE
                        chatsAdapter.submitList(chatsList())
                    }
                }
            }
        }
    }
}
