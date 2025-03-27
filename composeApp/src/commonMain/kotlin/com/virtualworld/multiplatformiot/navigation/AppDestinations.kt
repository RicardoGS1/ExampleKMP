package com.virtualworld.multiplatformiot.navigation

interface AppDestinations {
    val route: String
    val title: String
}




object RouteHome : AppDestinations {
    override val route = "home"
    override val title = "Home"
}

object RouteProfile : AppDestinations {
    override val route = "profile"
    override val title = "Usuaria"
}
