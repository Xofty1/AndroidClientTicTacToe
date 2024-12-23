package com.tictactoe.datasource.room.di

import com.tictactoe.MainApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkServiceModule::class])
interface DatasourceComponent {
    fun inject(app: MainApp)
}