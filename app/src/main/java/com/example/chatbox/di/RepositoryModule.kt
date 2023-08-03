package com.example.chatbox.di

import com.example.chatbox.data.remote.services.ChatsService
import com.example.chatbox.data.repository.ChatsRepositoryImpl
import com.example.chatbox.domain.repository.ChatsRepository
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

}