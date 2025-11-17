package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.dataCoreModule
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataConectionInternetModule = module {

    includes(dataCoreModule)

    single { Firebase.firestore }

    singleOf(::RemoteDataSource)

    single<RepositoryInternet> {  RepositoryInternetImp (get()) }




}