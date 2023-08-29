package com.example.chatbox.ui.userProfile

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.chatbox.R
import com.example.chatbox.common.base.BaseFragment
import com.example.chatbox.data.models.FriendRequestModel
import com.example.chatbox.databinding.FragmentUserProfileBinding
import com.example.chatbox.extentions.bottomNavBarVisibility
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    private val viewModel: UserProfileViewModel by viewModels()

    private val args: UserProfileFragmentArgs by navArgs()

    private lateinit var friendRequestModel: FriendRequestModel

    override fun started() {
        // Hide Bottom Nav Bar
        requireActivity().bottomNavBarVisibility(View.GONE)
    }

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.ibAddAsFriend.setOnClickListener {
            sendFriendRequest()
        }
    }

    override fun observers() {
        if (view != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getUserInfo(userId = args.userId)
                    viewModel.getUserProfile.collect { userInfo ->
                        val data = userInfo.data
                        Log.d("CheckIfDataReturned", "getChats: $data")
                        data?.let {
                            friendRequestModel = FriendRequestModel(
                                userId = data.id,
                                bio = data.bio,
                                name = data.name,
                                type = "Friend Request"
                            )
                            updateUi(userInfo)
                        }
                    }
                }
            }
        }
    }

    private fun sendFriendRequest() {

        var lastChild = "0"
        val notificationsReference =
            FirebaseDatabase.getInstance().getReference("Users/${args.userId}/notifications")

        notificationsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    lastChild = (snapshot.children.last().key?.toInt()?.plus(1)).toString()

                }

                notificationsReference.child(lastChild)
                    .setValue(friendRequestModel)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        if (args.isFriend) {
            Log.d("DoesItWork?", "sendFriendRequest: button is clicked")

            with(binding) {

                if (ibAddAsFriend.drawable == ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_add_friend,
                        null
                    )
                ) {
                    ibAddAsFriend.setImageResource(R.drawable.ic_request_sent)
                } else {
                    ibAddAsFriend.setImageResource(R.drawable.ic_add_friend)
                }
            }
        }

    }

    private fun updateUi(userInfo: UserProfileViewModel.UserInfoApiState?) {
        if (userInfo != null) {
            with(binding) {
                if (args.isFriend) {
                    tvUserName.text = userInfo.data?.name
                    tvDisplayName.text = userInfo.data?.name
                    tvUserTagName.text = "@${userInfo.data?.name}"
                    tvEmailAddress.text = userInfo.data?.email
                    ibStartVideoCall.visibility = View.GONE
                    ibStartVoiceCall.visibility = View.GONE
                    ibOpenChat.visibility = View.GONE
                    ibAddAsFriend.visibility = View.VISIBLE
                    // I Must Make Add User Button Visible And Finish The Flow Already.

                } else {
                    tvUserName.text = userInfo.data?.name
                    tvDisplayName.text = userInfo.data?.name
                    tvUserTagName.text = "@${userInfo.data?.name}"
                    tvEmailAddress.text = userInfo.data?.email
                }
            }
        } else {
            Toast.makeText(requireActivity(), "Something Went Wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}