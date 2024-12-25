package com.tictactoe.domain.di

import com.tictactoe.datasource.room.DatabaseService
import com.tictactoe.datasource.room.TicTacToeDatabase
import com.tictactoe.domain.repository.AuthRepository
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
    fun provideDatabaseService(db: TicTacToeDatabase): DatabaseService {
        return DatabaseService(db)
    }

    @Provides
    @Singleton
    fun provideUserRepository(databaseService: DatabaseService): AuthRepository {
        return AuthRepository(databaseService)
    }
}