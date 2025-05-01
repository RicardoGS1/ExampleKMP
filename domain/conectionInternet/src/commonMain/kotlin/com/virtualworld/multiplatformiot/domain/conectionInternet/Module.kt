package com.virtualworld.multiplatformiot.domain.conectionInternet


import com.virtualworld.conectioninternet.data.conectionInternet.RemoteDataSource
import com.virtualworld.conectioninternet.data.conectionInternet.RepositoryInternetImp
import com.virtualworld.conectioninternet.data.conectionInternet.dataConectionInternetModule
import com.virtualworld.conectioninternet.data.conectionInternet.RepocitoryInternet
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val domainConectionInternetModule = module {

    includes(dataConectionInternetModule)


    factoryOf(::UseCaseInternet)



}