package com.tictactoe.datasource.room.di

import android.content.Context
import androidx.room.Room
import com.tictactoe.datasource.room.TicTacToeDatabase
import com.tictactoe.datasource.room.dao.CurrentUserDao
import com.tictactoe.datasource.room.dao.GameDao
import com.tictactoe.datasource.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TicTacToeDatabase {
        return Room.databaseBuilder(
            context,
            TicTacToeDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGameDao(db: TicTacToeDatabase): GameDao = db.gameDao()

    @Provides
    @Singleton
    fun provideUserDao(db: TicTacToeDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideCurrentUserDao(db: TicTacToeDatabase): CurrentUserDao = db.currentUserDao()
}
