package dev.yuyuyuyuyu.getrandomint

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform