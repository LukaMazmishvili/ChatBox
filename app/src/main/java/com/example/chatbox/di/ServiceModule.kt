package com.example.chatbox.di

import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.data.remote.services.ContactService
import com.example.chatbox.data.remote.services.UserProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun getChats(retrofit: Retrofit) :ChatsService {
        return retrofit.create(ChatsService::class.java)
    }

    @Provides
    @Singleton
    fun getContacts(retrofit: Retrofit) : ContactService {
        return retrofit.create(ContactService::class.java)
    }

    @Provides
    @Singleton
    fun getUserProfile(retrofit: Retrofit) : UserProfileService {
        return retrofit.create(UserProfileService::class.java)
    }
}