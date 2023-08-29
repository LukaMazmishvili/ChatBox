package com.example.chatbox.ui.contacts

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.common.contactsList
import com.example.chatbox.databinding.FragmentContactsBinding
import com.example.chatbox.extentions.bottomNavBarVisibility
import com.example.chatbox.ui.contacts.adapters.ContactsAdapter
import com.example.chatbox.ui.home.HomeVM
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    private val viewModel: ContactsViewModel by viewModels()

    private val contactsAdapter by lazy {
        ContactsAdapter()
    }

    override fun started() {

        // Show Bottom Nav Bar
        requireActivity().bottomNavBarVisibility(View.VISIBLE)

        setUpRecyclerView()

        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("contact").setValue(
                contactsList()
            )

    }

    override fun listeners() {

        contactsAdapter.onItemClicked = { user ->
            findNavController().navigate(
                ContactsFragmentDirections.actionGlobalUserProfile(
                    userId = user.userId,
                    isFriend = true
                )
            )
        }

    }

    override fun observers() {
        if (view != null) {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getContacts(currentUserId)
                    viewModel.getContactsState.collect { contactList ->
                        val data = contactList.data
                        Log.d("CheckIfDataReturned", "getChats: $data")
                        data?.let {
                            updateUi(contactList)
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvContacts.adapter = contactsAdapter
    }

    private fun updateUi(contactsList: ContactsViewModel.ContactApiState?) {
        if (contactsList != null) {
            val data = contactsList.data
            if (data!!.isNotEmpty()) {
                Log.d("CheckIfDataReachedUI", "getChats: $data")
                binding.tvNoContacts.visibility = View.GONE
                binding.rvContacts.visibility = View.VISIBLE
                contactsAdapter.submitList(data.toList())
            }
        } else {
            binding.tvNoContacts.visibility = View.VISIBLE
            binding.rvContacts.visibility = View.GONE
        }
    }

}