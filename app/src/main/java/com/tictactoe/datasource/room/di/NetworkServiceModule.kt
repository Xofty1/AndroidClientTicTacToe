package com.tictactoe.datasource.room.di

import com.tictactoe.datasource.retrofit.NetworkService
import com.tictactoe.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideNetworkService(userRepository: UserRepository): NetworkService {
        return NetworkService(userRepository)
    }
}