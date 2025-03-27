package com.virtualworld.multiplatformiot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform