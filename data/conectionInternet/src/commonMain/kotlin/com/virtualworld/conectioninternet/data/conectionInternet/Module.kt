package com.virtualworld.conectioninternet.data.conectionInternet

import com.virtualworld.multiplatformiot.data.core.dataCoreModule
import com.virtualworld.multiplatformiot.domain.conectionInternet.repository.RepositoryInternet
import com.virtualworld.multiplatformiot.domain.login.repository.AuthRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val dataConectionInternetModule = module {

    includes(dataCoreModule)

    single { Firebase.firestore }

    single { RemoteDataSource(get(), get<AuthRepository>()) }

    single<RepositoryInternet> {  RepositoryInternetImp (get()) }




}