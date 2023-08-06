package com.example.chatbox.ui.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.chatsList
import com.example.chatbox.common.storiesList
import com.example.chatbox.databinding.FragmentHomeBinding
import com.example.chatbox.ui.home.adapters.ChatsAdapter
import com.example.chatbox.ui.home.adapters.StoriesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
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
        with(binding) {
            fabAddChat.setOnClickListener {
                FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats").child("1").removeValue()
                checkDataBaseBranch()
            }

            srlRefreshLayout.setOnRefreshListener {
//                chatsAdapter.submitList(emptyList())
                checkDataBaseBranch()
            }

            chatsAdapter.onItemLongClicked = {
                FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats").child("1").removeValue()
            }

        }
    }

    private fun setUpRecyclerViews() {
        with(binding) {
            rvStories.adapter = storiesAdapter
            rvChats.adapter = chatsAdapter
        }

        // Submit Lists to Recycler Views.
        storiesAdapter.submitList(storiesList())

        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats").setValue(chatsList())
    }

    private fun checkDataBaseBranch() {
        val state = FirebaseDatabase.getInstance().getReference("Users")

        state.child(FirebaseAuth.getInstance().currentUser!!.uid).child("chats")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        observer()

                        Log.d("CheckIfBranchExists", "getChats: ${snapshot.value}")
                    } else {
                        binding.tvNoConversations.visibility = View.VISIBLE
                        binding.rvChats.visibility = View.GONE
                        binding.srlRefreshLayout.isRefreshing = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun observer() {
        if(view != null) {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getChats(currentUserId)
                    viewModel.getChatsState.collect { chatsList ->
                        val data = chatsList.data
                        Log.d("CheckIfDataReturned", "getChats: $data")
                        data?.let {
                            if (data.isNotEmpty()) {
                                Log.d("CheckIfDataReachedUI", "getChats: $data")
                                binding.srlRefreshLayout.isRefreshing = chatsList.isLoading
                                binding.tvNoConversations.visibility = View.GONE
                                binding.rvChats.visibility = View.VISIBLE
                                chatsAdapter.submitList(data)
                            } else {
                                binding.tvNoConversations.visibility = View.VISIBLE
                                binding.rvChats.visibility = View.GONE
                                binding.srlRefreshLayout.isRefreshing = chatsList.isLoading
                            }
                        }
                    }
                }
            }
        }
    }
}
