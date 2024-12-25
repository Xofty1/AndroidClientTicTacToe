package com.tictactoe.domain.di

import com.tictactoe.MainApp
import com.tictactoe.datasource.room.di.NetworkServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, NetworkServiceModule::class])
interface DomainComponent {
    fun inject(app: MainApp)
}