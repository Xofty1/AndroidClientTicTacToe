package com.tictactoe

import android.app.Application
import android.content.Context
import com.tictactoe.datasource.room.di.DaggerDatasourceComponent
import com.tictactoe.datasource.room.di.DatasourceComponent

class MainApp : Application() {
    lateinit var datasourceComponent: DatasourceComponent

    override fun onCreate() {
        super.onCreate()
        datasourceComponent = DaggerDatasourceComponent.create()
    }
}

val Context.datasourceComponent: DatasourceComponent
    get() = when (this){
        is MainApp -> datasourceComponent
        else -> this.applicationContext.datasourceComponent
    }

