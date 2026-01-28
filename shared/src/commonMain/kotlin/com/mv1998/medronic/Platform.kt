package com.mv1998.medronic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform