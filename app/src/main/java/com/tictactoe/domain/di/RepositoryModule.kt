package com.tictactoe.domain.di

import android.content.Context
import androidx.room.Room
import com.tictactoe.datasource.room.TicTacToeDatabase
import com.tictactoe.datasource.room.dao.CurrentUserDao
import com.tictactoe.datasource.room.dao.GameDao
import com.tictactoe.datasource.room.dao.UserDao
import com.tictactoe.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao): UserRepository = UserRepository(dao)

}
