package com.virtualworld.multiplatformiot

import android.app.Application
import com.virtualworld.multiplatformiot.id.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MultiplatformIoTApp : Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MultiplatformIoTApp)
        }
    }


}