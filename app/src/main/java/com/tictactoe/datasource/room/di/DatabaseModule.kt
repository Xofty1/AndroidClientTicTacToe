package com.tictactoe.datasource.room.di

import android.content.Context
import androidx.room.Room
import com.tictactoe.datasource.room.TicTacToeDatabase
import com.tictactoe.datasource.room.dao.CurrentUserDao
import com.tictactoe.datasource.room.dao.GameDao
import com.tictactoe.datasource.room.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import dagger.Component
import dagger.Component.Factory
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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
    fun provideGameDao(db: TicTacToeDatabase): GameDao = db.gameDao()

    @Provides
    fun provideUserDao(db: TicTacToeDatabase): UserDao = db.userDao()

    @Provides
    fun provideCurrentUserDao(db: TicTacToeDatabase): CurrentUserDao = db.currentUserDao()
}
