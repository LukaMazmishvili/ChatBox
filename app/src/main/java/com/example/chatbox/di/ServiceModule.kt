package com.example.chatbox.di

import com.example.chatbox.data.remote.services.ChatsService
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
}