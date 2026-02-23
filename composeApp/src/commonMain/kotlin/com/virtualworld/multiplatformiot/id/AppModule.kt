package com.virtualworld.multiplatformiot.id

import com.virtualworld.conectioninternet.data.conectionInternet.dataConectionInternetModule
import com.virtualworld.multiplatformiot.data.connectionBLE.dataConnectionBLEModule
import com.virtualworld.multiplatformiot.data.connectionBluetooth.dataConnectionBluetoothModule
import com.virtualworld.multiplatformiot.data.login.dataLoginModule
import com.virtualworld.multiplatformiot.feature.conectionInternet.id.featureConectionInternetModule
import com.virtualworld.multiplatformiot.feature.login.id.featureLoginModule
import com.virtualworld.multiplatformiot.feature.menu.id.featureMenuModule
import com.virtualworld.multiplatformiot.feature.profile.id.featureProfileModule
import com.wirtualworld.multiplatformiot.feature.conectionLocal.featureConectionLocalModule
import com.wirtualworld.multiplatformiot.feature.connectionBLE.id.featureConnectionBLEModule
import com.wirtualworld.multiplatformiot.feature.connectionBluetooth.id.featureConnectionBluetoothModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataLoginModule,
        featureLoginModule,
        featureMenuModule,
        featureProfileModule,
        featureConectionLocalModule,
        featureConectionInternetModule,
        featureConnectionBluetoothModule,
        featureConnectionBLEModule,

        dataConectionInternetModule,
        dataConnectionBluetoothModule,
        dataConnectionBLEModule

    )

}