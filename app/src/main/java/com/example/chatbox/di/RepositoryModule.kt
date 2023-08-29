package com.example.chatbox.di

import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.data.remote.services.ContactService
import com.example.chatbox.data.remote.services.UserProfileService
import com.example.chatbox.data.repository.ChatsRepositoryImpl
import com.example.chatbox.data.repository.ContactsRepositoryImpl
import com.example.chatbox.data.repository.UserProfileRepositoryImpl
import com.example.chatbox.domain.repository.ChatsRepository
import com.example.chatbox.domain.repository.ContactsRepository
import com.example.chatbox.domain.repository.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChatsRepositoryImpl(chatsService: ChatsService): ChatsRepository {
        return ChatsRepositoryImpl(chatsService)
    }

    @Provides
    @Singleton
    fun provideContactsRepository(contactService: ContactService): ContactsRepository {
        return ContactsRepositoryImpl(contactService)
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(userProfileService: UserProfileService): UserProfileRepository {
        return UserProfileRepositoryImpl(userProfileService)
    }

}