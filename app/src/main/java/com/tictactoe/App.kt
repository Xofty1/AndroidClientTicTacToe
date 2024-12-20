package com.tictactoe

import android.app.Application
import android.content.Context
import com.tictactoe.datasource.room.di.DaggerDatasourceComponent
import com.tictactoe.datasource.room.di.DatasourceComponent
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApp : Application()

//val Context.datasourceComponent: DatasourceComponent
//    get() = when (this){
//        is MainApp -> datasourceComponent
//        else -> this.applicationContext.datasourceComponent
//    }

