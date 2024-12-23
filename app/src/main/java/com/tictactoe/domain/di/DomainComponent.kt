package com.tictactoe.domain.di

import com.tictactoe.MainApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface DomainComponent {
    fun inject(app: MainApp)
}